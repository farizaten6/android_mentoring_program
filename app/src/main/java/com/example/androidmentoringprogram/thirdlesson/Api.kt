package com.example.androidmentoringprogram.thirdlesson

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val URL = "https://newsapi.org"
private const val KEY = "0e57927e6bd54135bc82c753cfdc8a0b"

interface Api {
    @Headers("X-Api-Key:$KEY")
    @GET("/v2/everything")
    suspend fun getNews(@Query("q") topic: String?, @Query("from") date: String?) : Response

    companion object {
        val instance: Api by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api::class.java)
        }
    }
}