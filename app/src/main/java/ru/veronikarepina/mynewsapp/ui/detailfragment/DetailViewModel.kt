package ru.veronikarepina.mynewsapp.ui.detailfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veronikarepina.mynewsapp.data.DataObject
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.utils.extensions.asLiveData

class DetailViewModel: ViewModel() {
    private val localRepository = DataObject.localRepository

    private val _detailArticle = MutableLiveData<Article>()
    val detailArticle = _detailArticle.asLiveData()

    fun setDetail(new: Article){
        _detailArticle.value = new
    }

    fun handleFAvorite(){
        val article = _detailArticle.value ?: return
        if (article.flag){
            delFavorite(article)
        } else{
            addToFavorite(article)
        }
    }

    private fun addToFavorite(new: Article){
        val actualArticle = new.copy(flag = true)
        setDetail(actualArticle)
        viewModelScope.launch {
            localRepository.addNew(new)
        }
    }

    private fun delFavorite(new: Article){
        val actualArticle = new.copy(flag = false)
        setDetail(actualArticle)
        viewModelScope.launch {
            localRepository.delNew(new.copy(flag = true))
        }
    }

}