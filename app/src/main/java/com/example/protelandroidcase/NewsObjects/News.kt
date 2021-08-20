package com.example.protelandroidcase.NewsObjects

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)