package com.basicsandroid.newsapp.Model.api

import com.basicsandroid.newsapp.Model.Category
import com.basicsandroid.newsapp.R

object Constants {
    val API_KEY="a929e6089adc4f7cbf484ac3537f4c0e"
    val categories= listOf(

        Category(
            "sports", R.drawable.sports,
            R.string.sports,R.color.red
        ),
        Category(
            "technology", R.drawable.politics,
            R.string.technology,R.color.blue_dark
        ),
        Category(
            "health", R.drawable.health,
            R.string.health,R.color.pink
        ),
        Category(
            "business", R.drawable.bussines,
            R.string.business,R.color.brown
        ),
        Category(
            "general", R.drawable.environment,
            R.string.general,R.color.blue
        ),
        Category(
            "science", R.drawable.science,
           R.string.science,R.color.yellow
        ),
    )
}
