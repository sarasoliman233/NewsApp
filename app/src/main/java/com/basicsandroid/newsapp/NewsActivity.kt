package com.basicsandroid.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basicsandroid.newsapp.Model.NewsItem
import com.basicsandroid.newsapp.ui.theme.NewsAppTheme
import com.basicsandroid.newsapp.ui.theme.green
import com.basicsandroid.newsapp.ui.theme.grey
import kotlinx.coroutines.launch

class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
   NewsScreenContent()
            }
        }
    }
}

@Composable
fun NewsScreenContent(){
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope= rememberCoroutineScope()
  ModalNavigationDrawer(
      drawerState=drawerState,
      drawerContent = {

      NavigationDrawerSheet()
  }) {
  Scaffold (topBar = {
     //Top App Bar
      NewsTopAppBar {
          scope.launch {
              drawerState.open()
          }
      }
  }){
      PaddingValues->
      NewsFragment(modifier = Modifier.padding(top=PaddingValues.calculateTopPadding()))
  }
  }


}
@Composable
fun NewsList(){
    val newsList= listOf(
        NewsItem("Why are football's biggest clubs starting a new \n" +
                "tournament?","3 Hours Ago","BBC news",R.drawable.football),
                NewsItem("Why are football's biggest clubs starting a new \n" +
                "tournament?","3 Hours Ago","BBC news",R.drawable.football),
    NewsItem("Why are football's biggest clubs starting a new \n" +
            "tournament?","3 Hours Ago","BBC news",R.drawable.football),
        NewsItem("Why are football's biggest clubs starting a new \n" +
                "tournament?","3 Hours Ago","BBC news",R.drawable.football),
        NewsItem("Why are football's biggest clubs starting a new \n" +
                "tournament?","3 Hours Ago","BBC news",R.drawable.football)
    )
    LazyColumn{
        items(newsList.size){position->
         NewsCard(model=newsList[position])
        }
    }

}
@Composable
fun NewsCard(model:NewsItem){
 Card (modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)){
     Image(painter = painterResource(id = R.drawable.football),
         contentDescription = stringResource(R.string.news_card_image),
         modifier = Modifier
             .fillMaxWidth()
             .fillMaxHeight(.3F),
         contentScale = ContentScale.Crop
     )
     Text(text = model.source?: "", Modifier.padding( vertical = 8.dp), color = grey, fontSize = 10.sp)
     Text(text = model.tittle?: "",Modifier.padding(vertical = 3.dp), fontSize = 14.sp, fontFamily = FontFamily(
         Font(R.font.poppins)
     ))
     Text(text = model.time?: "",Modifier.padding(horizontal = 3.dp).align(Alignment.End), color = grey, fontSize = 13.sp)
 }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun NewsCardPreview(){
    NewsCard(model = NewsItem(
        "Why are football's biggest clubs starting a new \n" +
                "tournament?","3 Hours Ago","BBC news",R.drawable.football
    )
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NewsListPreview(){
NewsList()
}
@Composable
fun NewsFragment(modifier: Modifier=Modifier){
    val selectedTapIndex= remember {
        mutableIntStateOf(0)
    }
    val sources= listOf(
        "ABC News",
        "ABC News",
        "ABC News",
        "ABC News",
        "ABC News"
    )
  Column (modifier=modifier){

      ScrollableTabRow(selectedTabIndex =selectedTapIndex.intValue , edgePadding = 8.dp, indicator ={}, divider = {}) {
          sources.forEachIndexed { index, item ->
           Tab(selected = index==selectedTapIndex.intValue, onClick = {
               selectedTapIndex.intValue=index
           },
         selectedContentColor =Color.White,
         unselectedContentColor = green
           ) {
           Text(text = item, modifier= if(selectedTapIndex.intValue==index)
               Modifier
                   .padding(8.dp)
                   .background(green, RoundedCornerShape(50))
                   .padding(vertical = 8.dp, horizontal = 13.dp)
           else
               Modifier
                   .padding(9.dp)
                   .border(2.dp, green, CircleShape)
                   .padding(vertical = 8.dp, horizontal = 15.dp)
           )
           }

          }
      }
      NewsList()
  }
}

@Composable
@Preview(showSystemUi = true , showBackground = true)
fun NewsFragmentPreview(){
    NewsFragment()
}

@Composable
@Preview(showBackground=true, showSystemUi =true)
fun NewsScreenPreview(){
NewsScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(onSideMenuClick:()->Unit){
    TopAppBar(
        navigationIcon = { Image(painter = painterResource(id = R.drawable.ic_menu ),
        contentDescription = stringResource(R.string.navigation_drawer_icon),
         modifier = Modifier
             .padding(8.dp)
             .clickable {
                 onSideMenuClick()
             })
                         },
        title = {
        Text(text = stringResource(R.string.news_app), modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
    },
    colors = TopAppBarDefaults.topAppBarColors(containerColor = green,
        titleContentColor = Color.White),
    modifier = Modifier.clip(RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomStart = 30.dp,
    bottomEnd = 30.dp
    )),
        actions = {
            Image(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(
                R.string.icon_search
            ),
        modifier = Modifier.padding(8.dp))
        }
        )
}
@Composable
@Preview()
fun NewsTopAppBarPreview(){
    NewsTopAppBar {}
}

@Composable
fun NavigationDrawerSheet(){
ModalDrawerSheet (modifier = Modifier.fillMaxWidth(0.7F), drawerContainerColor = Color.White){
    Column (
        modifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(.2F)
            .background(color = green)
        , verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "News App!", fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

}
    NavigationDrawerItem(iconResId = R.drawable.ic_categ, textResId =R.string.categories )
    NavigationDrawerItem(iconResId = R.drawable.ic_setting, textResId =R.string.settings )
    
}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NavigationDrawerSheetPreview(){
  NavigationDrawerSheet()
}

@Composable
fun NavigationDrawerItem(iconResId:Int , textResId:Int){
  Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
   Image(painter = painterResource(id = iconResId),
       contentDescription = stringResource(R.string.navigation_item_icon)
       
   )
      Spacer(modifier = Modifier.padding(8.dp))
      Text(text = stringResource(id = textResId), fontSize = 24.sp, fontWeight = FontWeight.Bold)




}
}