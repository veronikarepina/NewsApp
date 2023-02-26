package ru.veronikarepina.mynewsapp.ui.firstfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.veronikarepina.mynewsapp.data.DataObject
import ru.veronikarepina.mynewsapp.model.Article
import ru.veronikarepina.mynewsapp.utils.extensions.asLiveData

class MainViewModel: ViewModel() {
    private val localRepository = DataObject.localRepository
    private val remoteRepository = DataObject.remoteRepository

    private val _articleLiveData = MutableLiveData<List<Article>?>()
    val articleLiveData = _articleLiveData.asLiveData()

    val favoriteArticles = localRepository.getAll

    init {
        getNews()
    }
    private fun getNews(){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                remoteRepository.getNews()
            }
            _articleLiveData.value = response.articles
        }
        handleAllArticle(favoriteArticles.value)
    }
    fun handleAllArticle(list: List<Article>?){
        _articleLiveData.value?.map { it.copy(flag = false) }?.toMutableList()
            ?.onEach { article ->
                list?.any { it.title == article.title }?.let { article.flag = it }
            }?.let { _articleLiveData.value = it }
    }
    fun handleFavorites(article: Article){
        if (article.flag){
            deleteFavorite(article)
        }
        else{
            addFavorite(article)
        }
    }

    private fun addFavorite(article: Article) = viewModelScope.launch{
        localRepository.addNew(article.copy(flag = true))
    }

    private fun deleteFavorite(article: Article) = viewModelScope.launch{
        localRepository.delNew(article)
        val oldList = _articleLiveData.value?.toMutableList()
        _articleLiveData.value =
            oldList?.map {
                it.copy(
                    flag = if (article.title == it.title){
                        false
                    }else{
                        it.flag
                    }
                )
            }
    }
}