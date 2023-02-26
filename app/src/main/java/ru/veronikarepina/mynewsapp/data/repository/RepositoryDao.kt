package ru.veronikarepina.mynewsapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import ru.veronikarepina.mynewsapp.data.database.Dao
import ru.veronikarepina.mynewsapp.model.Article

class RepositoryDao(private val newDao: Dao) {
    val getAll: LiveData<List<Article>> = newDao.getAllNews()
    suspend fun addNewRepository(new: Article){
        newDao.addNewDao(new)
    }
    suspend fun delNewRepository(new: Article){
        newDao.deleteNewDao(new)
    }
    suspend fun checkEmptyRepository(): Int {
        return newDao.checkEmpty()
    }
    fun getFlagRepository(title: String?):Int{
        Log.d("MyLog", "REPOSITORY DAO: getFlagRepo")
        return newDao.getFlag(title)
    }
    fun searchNewRepository(title: String?): Article{
        return newDao.searchNew(title)
    }
}