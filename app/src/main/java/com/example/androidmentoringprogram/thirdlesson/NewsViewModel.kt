package com.example.androidmentoringprogram.thirdlesson

import android.app.ProgressDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val LOADING_MESSAGE = "Loading..."

class NewsViewModel: ViewModel() {
    var news = MutableLiveData<List<Article>>()

    fun getNews(topic: String, date: String, mProgressDialog: ProgressDialog): MutableLiveData<List<Article>> {
        mProgressDialog.setMessage(LOADING_MESSAGE)
        mProgressDialog.show()

        loadNews(topic, date)
        return news
    }

    private fun loadNews(topic: String, date: String) {
        var articles: List<Article>
        viewModelScope.launch(Dispatchers.IO) {
            articles = try {
                Api.instance.getNews(topic, date).articles?.subList(0, 20) ?: emptyList()
            } catch (exception: Exception) {
                emptyList()
            }
            news.postValue(articles)
        }
    }
}