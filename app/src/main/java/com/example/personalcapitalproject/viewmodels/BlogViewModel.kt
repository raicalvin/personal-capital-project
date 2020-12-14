package com.example.personalcapitalproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcapitalproject.models.Article
import com.example.personalcapitalproject.models.ArticleWrapper
import com.example.personalcapitalproject.models.ItemType
import com.example.personalcapitalproject.repositories.BlogRepository
import kotlinx.coroutines.launch

class BlogViewModel : ViewModel() {

    private val blogApi = BlogRepository()

    fun viewCreated() {
        getBlogData()
    }

    private fun getBlogData() {
        viewModelScope.launch { blogApi.fetchBlogData() }
    }

    fun wrappedBlogArticles(): LiveData<List<ArticleWrapper>> {
        return Transformations.map(blogApi.blogResponseLiveData) { blogResponse ->
            val articles = blogResponse.items
            wrapArticles(articles)
        }
    }

    /**
     * Wraps an incoming list of Article objects in the ArticleWrapper class which is used to
     * distinguish view items from one another in the RecyclerView.Adapter to create the
     * appropriate ViewHolder object.
     *
     * @param articles the list of Article objects from the Blog Response
     */
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