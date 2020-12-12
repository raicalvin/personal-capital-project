package com.example.personalcapitalproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcapitalproject.helpers.hogwarts
import com.example.personalcapitalproject.models.BlogResponse
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

}