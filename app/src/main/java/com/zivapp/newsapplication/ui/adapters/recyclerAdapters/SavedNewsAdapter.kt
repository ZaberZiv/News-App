package com.zivapp.newsapplication.ui.adapters.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.ItemNewsBinding
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.utils.loadWithCoil

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<ArticlesDto>() {
        override fun areItemsTheSame(oldItem: ArticlesDto, newItem: ArticlesDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticlesDto, newItem: ArticlesDto): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = differ.currentList[position]

            bookmarkGroup.isVisible = true
            tvDateSeparator.isVisible = false
            contentCard.isVisible = true

            tvTitle.text = item.title
            tvDetails.text =
                root.resources.getString(R.string.details_news, item.description)

            tvAuthor.text =
                if (item.author == null) root.resources.getString(R.string.author_unknown)
                else root.resources.getString(R.string.author_news, item.author)

            loadWithCoil(ivBanner, item.urlToImage)

            if (item.isPicked) {
                ivBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
            } else {
                ivBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            }

            ivBookmark.setOnClickListener {
                onBookmarkClickListener?.let { it(item) }
                item.isPicked = !item.isPicked
                notifyItemChanged(position)
            }

            root.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    private var onItemClickListener: ((ArticlesDto) -> Unit)? = null
    fun setOnItemClickListener(listener: (ArticlesDto) -> Unit) {
        onItemClickListener = listener
    }

    private var onBookmarkClickListener: ((ArticlesDto) -> Unit)? = null
    fun setOnBookmarkClickListener(listener: (ArticlesDto) -> Unit) {
        onBookmarkClickListener = listener
    }
}