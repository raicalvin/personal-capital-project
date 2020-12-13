package com.example.personalcapitalproject.helpers

import androidx.recyclerview.widget.DiffUtil
import com.example.personalcapitalproject.models.ArticleWrapper

class ArticleDiffUtil : DiffUtil.ItemCallback<ArticleWrapper>() {
    override fun areItemsTheSame(oldItem: ArticleWrapper, newItem: ArticleWrapper): Boolean {
        return oldItem.article?.id == newItem.article?.id
    }

    override fun areContentsTheSame(oldItem: ArticleWrapper, newItem: ArticleWrapper): Boolean {
        return oldItem == newItem
    }
}