package com.basicsandroid.newsapp.fragments.newsDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.basicsandroid.newsapp.Model.api.ArticlesItem
import com.basicsandroid.newsapp.Model.api.ArticlesResponse
import com.basicsandroid.newsapp.Model.api.Constants
import com.basicsandroid.newsapp.NewsActivity
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.api.APIManager
import com.basicsandroid.newsapp.ui.theme.textColor
import com.basicsandroid.newsapp.utils.NewsCard
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsDetailsScreen(vm: NewsDetailsViewModel=viewModel(),title:String,scope:CoroutineScope,drawerState: DrawerState){
    val toolbarTitle= remember {
        mutableIntStateOf(R.string.news_app)
    }



    if(vm.newsItem==null) {
        LaunchedEffect(key1 = Unit) {
            vm.getNewsItem(title)
        }
    }
    


    Scaffold (

    ){
        paddingValues ->
vm.newsItem?.let {
    NewsDetailsContent(paddingValues,it )
}

    }
}

@Composable
fun NewsDetailsContent(paddingValues: PaddingValues,newsItem: ArticlesItem) {
    Column (
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .paint(painterResource(R.drawable.pattern), contentScale = ContentScale.Crop)
            .verticalScroll(
                rememberScrollState()
            )){
       NewsCard(model =newsItem )
        NewsDetailsCard(newsItem)
    }

}

@Composable
fun NewsDetailsCard(newsItem: ArticlesItem) {
    val context= (LocalContext.current) as NewsActivity
    Column (modifier = Modifier
        .padding(10.dp)
        .background(Color.White, RoundedCornerShape(10.dp))
        .padding(8.dp)){

                 Text(text = newsItem.content?:"",
                     modifier = Modifier.padding(8.dp),
                     color = textColor,
                     fontFamily = FontFamily(Font(R.font.poppins))
                 )
        Spacer(modifier = Modifier.fillMaxHeight(.7f))
        Row (modifier = Modifier.fillMaxWidth().clickable {
            context.openWebSiteForNews(newsItem.url?:"")
        }){
            Spacer(modifier =Modifier.fillMaxWidth(.6f) )
            Text(text ="View Full Article",
                modifier = Modifier.padding(8.dp)
                 , fontFamily =  FontFamily(Font(R.font.poppins_bold)),
                 color= textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium

                )

            Image(painter = painterResource(id = R.drawable.right_arrow) , contentDescription ="icon_arrow" ,Modifier.size(27.dp).padding(top = 8.dp))
        }
    }

}

@Composable
@Preview
private fun PreviewNewsDetailsScreen(){

}
