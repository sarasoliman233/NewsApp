package com.basicsandroid.newsapp.fragments.newsDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.basicsandroid.newsapp.Model.api.ArticlesItem
import com.basicsandroid.newsapp.Model.api.ArticlesResponse
import com.basicsandroid.newsapp.Model.api.Constants
import com.basicsandroid.newsapp.api.APIManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsViewModel :ViewModel() {
    var newsItem by mutableStateOf<ArticlesItem?>(null)
    var getNewsItemCall: Call<ArticlesResponse>?=null
    fun getNewsItem(title:String){
        getNewsItemCall= APIManager.getNewsServises().getNewsItem(title=title, apiKey = Constants.API_KEY)
        getNewsItemCall?.enqueue(object: Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful){
                    val news=response.body()?.articles?.get(0)
                   newsItem = news
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
            }

        }

        )
    }

    override fun onCleared() {
        super.onCleared()
        getNewsItemCall?.cancel()
    }

}