package com.example.personalcapitalproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcapitalproject.helpers.hogwarts
import com.example.personalcapitalproject.models.Article
import com.example.personalcapitalproject.models.ArticleWrapper
import com.example.personalcapitalproject.models.BlogResponse
import com.example.personalcapitalproject.models.ItemType
import com.example.personalcapitalproject.repositories.BlogRepository
import kotlinx.coroutines.launch

class BlogViewModel : ViewModel() {

    private val blogApi = BlogRepository()

    fun viewCreated() {
        getBlogData()
    }

    private fun getBlogData() {
        viewModelScope.launch {
            val data = blogApi.fetchBlogData()
            hogwarts("From ViewModel: $data")
        }
    }

    fun blogResponse(): LiveData<BlogResponse> = blogApi.blogResponseLiveData

    fun blogResponseTransformed(): LiveData<List<ArticleWrapper>> {
        return Transformations.map(blogApi.blogResponseLiveData) { blogResponse ->
            val articles = blogResponse.items
            wrapArticles(articles)
        }
    }

    private fun wrapArticles(articles: List<Article>): List<ArticleWrapper> {
        val wrappedArticles = mutableListOf<ArticleWrapper>()
        articles.forEachIndexed { index, article ->
            if (index != 0) {
                wrappedArticles.add(ArticleWrapper(article, ItemType.ARTICLE))
            } else {
                wrappedArticles.add(ArticleWrapper(article, ItemType.HEADER_ARTICLE))
                wrappedArticles.add(ArticleWrapper(null, ItemType.SECTION_TITLE))
            }
        }
        return wrappedArticles
    }

}