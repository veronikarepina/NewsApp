package ru.veronikarepina.mynewsapp.data

import android.content.Context
import ru.veronikarepina.mynewsapp.data.database.NewsDataBase
import ru.veronikarepina.mynewsapp.data.repository.Repository
import ru.veronikarepina.mynewsapp.data.repository.RepositoryDao

object DataObject {
    lateinit var dataBase: NewsDataBase
    lateinit var remoteRepository: Repository
    lateinit var localRepository: RepositoryDao

    fun initData(context: Context){
        dataBase = NewsDataBase.getDataBase(context)
        remoteRepository = Repository()
        localRepository = RepositoryDao(dataBase.newsDao())
    }
}