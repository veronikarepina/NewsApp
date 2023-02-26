package ru.veronikarepina.mynewsapp.ui.secondfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.veronikarepina.mynewsapp.data.database.NewsDataBase
import ru.veronikarepina.mynewsapp.data.repository.RepositoryDao
import ru.veronikarepina.mynewsapp.model.Article

class DaoViewModel(application: Application): AndroidViewModel(application) {
    val getAll: LiveData<List<Article>>
    private val repository: RepositoryDao

    init {
        val newDao = NewsDataBase.getDataBase(application).newsDao()
        repository = RepositoryDao(newDao)
        getAll = repository.getAll
    }
    fun addNew(new: Article){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNewRepository(new)
        }
    }
    fun delNew(new: Article){
        viewModelScope.launch(Dispatchers.IO){
            repository.delNewRepository(new)
        }
    }
    suspend fun checkEmpty(): Int {
        return repository.checkEmptyRepository()
    }
    suspend fun getFlag(title: String?): Int = viewModelScope.async(Dispatchers.IO) {
            return@async repository.getFlagRepository(title)
    }.await()
    fun searchNewToDelete(title: String?){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delNewRepository(repository.searchNewRepository(title))
        }
    }
}