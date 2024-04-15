package com.basicsandroid.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// عشان نعمل object من ال retrofit
object APIManager {
    private val httpLoggingInerceptor= HttpLoggingInterceptor{ message->
        Log.e("API",message)

    }.apply {
        level=HttpLoggingInterceptor.Level.BODY
    }
    private val client=OkHttpClient.Builder()
        .addInterceptor(httpLoggingInerceptor)
        .build()
    private  val retrofit:Retrofit=Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getNewsServises():NewsServices{  //هتبقا من نوع NewsServices

        return retrofit.create(NewsServices::class.java)  //وهنا بتعمل implement للفانكشن الي جوا الكلاس

    }

}