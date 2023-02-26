package ru.veronikarepina.mynewsapp.ui

import ru.veronikarepina.mynewsapp.model.Article

interface Listener {
    fun addNewDb(new: Article)
    fun delNewDb(new: Article)
    suspend fun checkFlag(title: String?): Int
    fun onClick(new: Article)
    fun searchAndDeleteNew(title: String?)
}