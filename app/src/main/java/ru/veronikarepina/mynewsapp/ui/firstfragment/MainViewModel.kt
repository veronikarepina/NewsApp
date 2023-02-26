package ru.veronikarepina.mynewsapp.ui.firstfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veronikarepina.mynewsapp.data.repository.Repository
import ru.veronikarepina.mynewsapp.model.NewsModel

class MainViewModel: ViewModel() {
    private val repos = Repository()
    val viewModelLiveData: MutableLiveData<NewsModel> = MutableLiveData()
    init {
        getNewsViewModel()
    }
    private fun getNewsViewModel(){
        viewModelScope.launch {
            viewModelLiveData.value = repos.getNews()
        }
    }
}