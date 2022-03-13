package com.example.myapplication

data class MapNews(
    val title: String,
    val name: String,
    val description: String,
    val iconUrl: String,
    val url: String
)

data class SourceNews(
    val name: String,
    val description: String
)

data class TopNews(
    val title: String,
    val name: String,
    val description: String,
    val iconUrl: String,
    val url: String
)

data class ResultNews(
    val title: String,
    val name: String,
    val description: String,
    val iconUrl: String,
    val url: String
)