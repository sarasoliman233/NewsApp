package com.basicsandroid.newsapp.api


import com.basicsandroid.newsapp.Model.api.ArticlesResponse
import com.basicsandroid.newsapp.Model.api.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {

    // API اللي هكلمها
    // https://newsapi.org/v2/top-headlines/sources?apiKey=API_KEY ...     sourcesالخاص ب URL دا ال
    @GET("top-headlines/sources")
    fun getNewsSources(
        @Query("apiKey") apiKey:String,
        @Query("category") category:String?
    ): Call<SourcesResponse>

    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=a929e6089adc4f7cbf484ac3537f4c0e  everything الخاص ب URL دا ال

    @GET("everything")
    fun getNewsBySource(
        @Query("apiKey") apiKey: String,
        @Query("sources") sourceId:String,

    ):Call<ArticlesResponse >

    @GET("everything")
    fun getNewsItem(
        @Query("q") title:String,
        @Query("searchIn") topic:String="title",
        @Query("apiKey") apiKey: String
    ):Call<ArticlesResponse>
    @GET("/v2/everything")
    fun getSearchedArticles(
        @Query("q")searchQuery:String,
        @Query("apiKey")apiKey:String)
    :Call<ArticlesResponse>

}