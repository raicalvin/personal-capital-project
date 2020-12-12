package com.example.personalcapitalproject.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalcapitalproject.R
import com.example.personalcapitalproject.helpers.ArticleDiffUtil
import com.example.personalcapitalproject.models.Article

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffUtil()) {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textView = view.findViewById<TextView>(R.id.test_row_item)

        fun bindData(article: Article) {
            textView.text = article.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val context = parent.context
        val textView = TextView(context)
        textView.id = R.id.test_row_item
        textView.setPadding(36)
        return ArticleViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bindData(article)
    }
}

