package com.basicsandroid.newsapp.fragments.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import com.basicsandroid.newsapp.Model.api.ArticlesItem
import com.basicsandroid.newsapp.Model.api.ArticlesResponse
import com.basicsandroid.newsapp.Model.api.Constants
import com.basicsandroid.newsapp.api.APIManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel:ViewModel() {
    var newsList by mutableStateOf(listOf<ArticlesItem>())

    var loadingState by mutableStateOf(false)

    var newsFoundState by mutableStateOf(false)

    var searchQuery by mutableStateOf("")


    var isFocused by  mutableStateOf(false)
    val focusRequester=  FocusRequester ()
    var getSearchArticlesCall: Call<ArticlesResponse>?=null

    var messageState= mutableStateOf<String?>(null)

    fun searchArticles(){
        loadingState=true
        getSearchArticlesCall=APIManager.getNewsServises().getSearchedArticles(searchQuery=searchQuery, apiKey = Constants.API_KEY)
        getSearchArticlesCall?.enqueue(object: Callback<ArticlesResponse>{
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if(response.isSuccessful){
                    val news=response.body()?.articles
                    if(!news.isNullOrEmpty()){
                        newsList=news
                        newsFoundState= true
                    }else{
                        newsFoundState=false
                    }
                }
                loadingState=false
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                loadingState=false
                searchQuery=""
            }


        })



    }

    override fun onCleared() {
        super.onCleared()
        getSearchArticlesCall?.cancel()
    }
}