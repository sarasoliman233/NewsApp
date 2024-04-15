package com.basicsandroid.newsapp.Model.api

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
//{
//"status": "ok",            <--------
//-"sources": [              <--------
//-{                                                    دا مثلا ال response
//"id": "abc-news",
//"name": "ABC News",
//"description": "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.",
//"url": "https://abcnews.go.com",
//"category": "general",
//"language": "en",
//"country": "us"
//},


	@field:SerializedName("sources")   //باخد نفس كلمه sources يالظبط
	val sources: List<SourcesItem>?=null,

	@field:SerializedName("status")  //وهنا نفس كلمه status
	val status: String? = null
)