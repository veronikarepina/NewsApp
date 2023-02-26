package ru.veronikarepina.mynewsapp.model

import androidx.room.Entity
import java.io.Serializable

@Entity (tableName = "news_table", primaryKeys = ["title"])
data class Article(
    val title: String = "",
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val url: String?,
    val urlToImage: String?,
    var flag: Boolean = false
): Serializable