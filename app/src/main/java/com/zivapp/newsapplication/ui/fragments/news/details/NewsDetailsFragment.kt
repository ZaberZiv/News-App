package com.zivapp.newsapplication.ui.fragments.news.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentNewsDetailsBinding
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.utils.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailsFragment : Fragment(),
    ViewBindingHolder<FragmentNewsDetailsBinding> by ViewBindingHolderImpl() {

    private val args = navArgs<NewsDetailsFragmentArgs>()

    private val article: ArticlesDto by lazy { args.value.article }

    private val viewModel by viewModel<NewsDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentNewsDetailsBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupUI()
        setupListeners(article)
    }

    private fun setupUI() = requireBinding {
        toolbar.setupToolbar(this@NewsDetailsFragment)
        toolbar.inflateMenu(R.menu.saved_news_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.empty_bookmark -> {
                    viewModel.insertArticle(article)
                    menuItemsVisibility(enabledIcon = false, disabledIcon = true)
                }
                R.id.saved_bookmark -> {
                    viewModel.deleteArticle(article.url)
                    menuItemsVisibility(enabledIcon = true, disabledIcon = false)
                }
            }
            true
        }

        setupMenuItems()

        with(article) {
            toolbar.title = title
            tvTitle.text = title
            tvContent.text = content

            loadWithCoil(ivBanner, urlToImage)

            tvAuthor.text = getString(R.string.author_news, author)
            tvDateOfPublish.text = getString(
                R.string.publish_date_news,
                DateUtils.formatGMTToUTCDateStr(publishedAt)
            )
            tvLink.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setupMenuItems() {
        lifecycleScope.launchWhenCreated {
            viewModel.getArticleByUrl(article.url).collect { articleDto ->
                val artcl = articleDto ?: article

                if (artcl.isPicked) menuItemsVisibility(enabledIcon = false, disabledIcon = true)
                else menuItemsVisibility(enabledIcon = true, disabledIcon = false)
            }
        }
    }

    private fun menuItemsVisibility(enabledIcon: Boolean, disabledIcon: Boolean) =
        requireBinding().toolbar.apply {
            menu.findItem(R.id.empty_bookmark).isVisible = enabledIcon
            menu.findItem(R.id.saved_bookmark).isVisible = disabledIcon
        }

    private fun setupListeners(article: ArticlesDto) = requireBinding {
        tvLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(browserIntent)
        }
    }
}