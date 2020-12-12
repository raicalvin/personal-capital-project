package com.example.personalcapitalproject.models

data class Article(
    val id: String,
    val url: String,
    val category: String,
    val encoded_title: String,
    val featured_image: String,
    val summary: String,
    val insight_summary: String,
    val content: String,
    val summary_html: String,
    val content_html: String,
    val date_published: String,
    val date_modified: String
)