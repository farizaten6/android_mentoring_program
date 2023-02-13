package com.example.androidmentoringprogram.thirdlesson

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    var news = MutableLiveData<List<Article>>()

    fun getNews(topic: String, date: String, context: Context): MutableLiveData<List<Article>> {
        loadNews(topic, date, context)
        return news
    }

    private fun loadNews(topic: String, date: String, context: Context) {
        var articles: List<Article>
        viewModelScope.launch(Dispatchers.IO) {
            try {
                articles = Api.instance.getNews(topic, date).articles.subList(0, 20)
                NewsActivity.status = "ok"
            } catch (exception: Exception) {
                articles =  emptyList()
                NewsActivity.status = "$exception"
            }
            news.postValue(articles)
        }
    }
}