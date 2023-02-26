package ru.veronikarepina.mynewsapp.data.repository

import ru.veronikarepina.mynewsapp.data.api.RetrofitInstance
import ru.veronikarepina.mynewsapp.model.NewsModel

class Repository {
    suspend fun getNews(): NewsModel{
        return RetrofitInstance.api.getNewsApi()
    }
}