package com.example.androidmentoringprogram.thirdlesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class NewsViewModel : ViewModel(), ContainerHost<ResponseState, Nothing> {

    override val container: Container<ResponseState, Nothing> = container(ResponseState("ok", 0, emptyList()))

    fun getNews(topic: String, date: String) = intent {
        var articles: List<Article>
        viewModelScope.launch(Dispatchers.IO) {
            try {
                articles = Api.instance.getNews(topic, date).articles.subList(0, 20)
                NewsActivity.status = "ok"
            } catch (exception: Exception) {
                articles =  emptyList()
                NewsActivity.status = "$exception"
            }
            reduce {
                state.copy(articles = articles)
            }
        }
    }
}