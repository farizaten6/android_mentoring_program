package com.example.androidmentoringprogram.thirdlesson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    var news = MutableLiveData<List<Article>>()

    fun getNews(topic: String): MutableLiveData<List<Article>> {
        loadNews(topic)
        return news
    }

    private fun loadNews(topic: String) {
        var articles: List<Article>
        viewModelScope.launch(Dispatchers.IO) {
            articles = try {
                Api.instance.getNews(topic).articles?.subList(0, 20) ?: emptyList()
            } catch (exception: Exception) {
                emptyList()
            }
            news.postValue(articles)
        }
    }
}