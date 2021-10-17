package com.zivapp.newsapplication.ui.fragments.news.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentNewsDetailsBinding
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.ui.activity.MainActivity
import com.zivapp.newsapplication.ui.fragments.news.NewsViewModel
import com.zivapp.newsapplication.utils.*
import kotlinx.coroutines.flow.collect

class NewsDetailsFragment : Fragment(),
    ViewBindingHolder<FragmentNewsDetailsBinding> by ViewBindingHolderImpl() {

    private val args = navArgs<NewsDetailsFragmentArgs>()

    private val article: ArticlesDto by lazy { args.value.article }

    private val viewModel: NewsViewModel by lazy { (activity as MainActivity).newsViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentNewsDetailsBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupUI()
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
            setupListeners(this)
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
        tvLink.setOnClickListener { _ ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(browserIntent)
        }
    }
}