package com.example.personalcapitalproject.models

data class BlogResponse(
    val version: String,
    val user_comment: String,
    val home_page_url: String,
    val feed_url: String,
    val title: String,
    val description: String,
    val items: List<Article>
)