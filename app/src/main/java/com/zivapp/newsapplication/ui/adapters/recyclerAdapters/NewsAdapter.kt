package com.zivapp.newsapplication.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.ItemNewsBinding
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.utils.loadWithCoil

class NewsAdapter : PagingDataAdapter<ArticlesDto, NewsAdapter.NewsViewHolder>(ORDER_COMPARATOR) {
    companion object {
        private val ORDER_COMPARATOR = object : DiffUtil.ItemCallback<ArticlesDto>() {
            override fun areItemsTheSame(oldItem: ArticlesDto, newItem: ArticlesDto): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticlesDto, newItem: ArticlesDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.binding.apply {

                if (item.title == "") {
                    tvDateSeparator.isVisible = true
                    contentCard.isVisible = false

                    tvDateSeparator.text = item.publishedAt
                } else {
                    tvDateSeparator.isVisible = false
                    contentCard.isVisible = true

                    tvTitle.text = item.title
                    tvDetails.text =
                        root.resources.getString(R.string.details_news, item.description)
                    tvAuthor.text = root.resources.getString(R.string.author_news, item.author)

                    loadWithCoil(ivBanner, item.urlToImage)
                }

                root.setOnClickListener {
                    onClickListener?.let { it(item) }
                }
            }
        }
    }

    private var onClickListener: ((ArticlesDto) -> Unit)? = null
    fun setOnNewsClickListener(listener: (ArticlesDto) -> Unit) {
        onClickListener = listener
    }
}