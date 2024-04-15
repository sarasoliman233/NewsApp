package com.basicsandroid.newsapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.basicsandroid.newsapp.Model.Category
import com.basicsandroid.newsapp.Model.api.Constants
import com.basicsandroid.newsapp.Model.api.SourcesItem
import com.basicsandroid.newsapp.Model.api.SourcesResponse
import com.basicsandroid.newsapp.api.APIManager
import com.basicsandroid.newsapp.ui.theme.green
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsSourcesTabRow(category: String,onTabSelected:(sourceId:String)->Unit){
    val selectedTapIndex= remember {
        mutableIntStateOf(0)
    }
//    val sources= listOf(
//        "ABC News",
//        "ABC News",
//        "ABC News",
//        "ABC News",
//        "ABC News"
//    )
    val sourcesList= remember {
        mutableStateListOf<SourcesItem>()
    }
    LaunchedEffect(Unit){
        APIManager
            .getNewsServises()
            .getNewsSources(Constants.API_KEY,category )
            .enqueue(object :Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
              val sources= response.body()?.sources
                    if(sources?.isNotEmpty()==true){
                        sourcesList.addAll(sources)
                    }

                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    if(sourcesList.isNotEmpty()){
LaunchedEffect(Unit){
    val sourceId=sourcesList.get(0).id
    onTabSelected(sourceId ?:"")
}
        
    }
    ScrollableTabRow(selectedTabIndex =selectedTapIndex.intValue , edgePadding = 8.dp, indicator ={}, divider = {}) {
        sourcesList.forEachIndexed { index, item ->
            Tab(selected = index==selectedTapIndex.intValue,
                onClick = {
                    onTabSelected(item.id?:"")
                selectedTapIndex.intValue=index
            },
                selectedContentColor =Color.White,
                unselectedContentColor = green
            ) {
                Text(text = item.name?:"", modifier= if(selectedTapIndex.intValue==index)
                    Modifier
                        .padding(8.dp)
                        .background(green, RoundedCornerShape(50))
                        .padding(vertical = 8.dp, horizontal = 13.dp)
                else
                    Modifier
                        .padding(8.dp)
                        .border(2.dp, green, CircleShape)
                        .padding(vertical = 8.dp, horizontal = 15.dp)
                )
            }

        }
    }
}