package com.uszkaisandor.mealdeas.shared

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uszkaisandor.mealdeas.R
import com.uszkaisandor.mealdeas.data.NewsArticle
import com.uszkaisandor.mealdeas.databinding.ItemNewsArticleBinding

class NewsArticleViewHolder(
    private val binding: ItemNewsArticleBinding,
    private val onItemClick: (Int) -> Unit,
    private val onBookmarkClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(newsArticle: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(newsArticle.thumbnailUrl)
                .error(R.drawable.image_placeholder)
                .into(imageView)

            textViewTitle.text = newsArticle.title ?: ""

            imageViewBookmark.setImageResource(
                when {
                    newsArticle.isBookmarked -> R.drawable.ic_bookmark_selected
                    else -> R.drawable.ic_bookmark_unselected
                }
            )
        }
    }

    init {
        binding.apply {
            root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(bindingAdapterPosition)
                }
            }

            imageViewBookmark.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onBookmarkClicked(bindingAdapterPosition)
                }
            }
        }
    }
}