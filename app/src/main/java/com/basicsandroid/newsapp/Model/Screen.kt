package com.basicsandroid.newsapp.Model

import com.basicsandroid.newsapp.R

open class Screen (val route:String , val description:Int)

class NewsScreen():Screen("news", R.string.news_app)

class CategoriesScreen():Screen("categories",R.string.categories)