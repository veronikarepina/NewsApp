package ru.veronikarepina.mynewsapp.ui.secondfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veronikarepina.mynewsapp.data.DataObject
import ru.veronikarepina.mynewsapp.data.repository.RepositoryDao
import ru.veronikarepina.mynewsapp.model.Article

class DaoViewModel(): ViewModel() {
    private val localRepository: RepositoryDao = DataObject.localRepository
    val favoriteArticles = localRepository.getAll

    fun delNew(new: Article) = viewModelScope.launch{
        localRepository.delNew(new)
    }

}