package com.basicsandroid.newsapp.fragments.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.basicsandroid.newsapp.Model.api.ArticlesItem
import com.basicsandroid.newsapp.Model.api.ArticlesResponse
import com.basicsandroid.newsapp.Model.api.Constants
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.api.APIManager
import com.basicsandroid.newsapp.utils.NewsCard
import com.basicsandroid.newsapp.utils.NewsSourcesTabRow
import com.basicsandroid.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsScreen(
    navHostController: NavHostController
    ,modifier: Modifier = Modifier
    ,category: String,

     onNewsClick: (String)-> Unit){
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope= rememberCoroutineScope()
    val navController= rememberNavController()

    val toolbarTitle= remember {
        mutableIntStateOf(R.string.news_app)
    }
    val newsListStates= remember {
        mutableStateListOf <ArticlesItem>()
    }
    Scaffold(
        topBar = {
            //Top App Bar
            NewsTopAppBar(
                titleResourceId = toolbarTitle.intValue,
                shouldDisplayMenuIcon = true,
                shouldDisplaySearchIcon = true,
                onSearchClick = {
                    navController.navigate("search")
                }) {
                scope.launch {
                    drawerState.open()
                }

            }
        }
    ) {
        paddingValues -> paddingValues
        Column (modifier=modifier
            .padding(top = paddingValues.calculateTopPadding())
            .paint(painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop)){
            NewsSourcesTabRow (category=category){sourceId->
                APIManager
                    .getNewsServises()
                    .getNewsBySource(Constants.API_KEY, sourceId)
                    .enqueue(object : Callback<ArticlesResponse> {
                        override fun onResponse(
                            call: Call<ArticlesResponse>,
                            response: Response<ArticlesResponse>
                        ) {
                            newsListStates.clear()
                            val newsList=response.body()?.articles
                            if(newsList?.isNotEmpty()==true){
                                newsListStates.addAll(newsList)
                            }
                        }

                        override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                        }

                    })
            }
            NewsList(newsListStates.toList()){
                onNewsClick(it)
            }
        }
    }

}



@Composable
fun NewsList(newsList:List<ArticlesItem>,onNewsClick:(String)->Unit){

    LazyColumn{
        items(newsList.size){position->
            NewsCard(model=newsList[position]){title->
                onNewsClick(title)

            }
        }
    }

}


