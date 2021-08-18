package com.example.protelandroidcase.news

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)