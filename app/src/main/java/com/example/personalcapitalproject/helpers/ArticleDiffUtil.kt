package com.example.personalcapitalproject.helpers

import androidx.recyclerview.widget.DiffUtil
import com.example.personalcapitalproject.models.Article

class ArticleDiffUtil : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}