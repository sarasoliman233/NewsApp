package com.basicsandroid.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.basicsandroid.newsapp.Model.CategoriesScreen
import com.basicsandroid.newsapp.Model.NewsScreen
import com.basicsandroid.newsapp.fragments.categoires.CategoriesScreen
import com.basicsandroid.newsapp.fragments.newsDetails.NewsDetailsScreen
import com.basicsandroid.newsapp.fragments.news.NewsScreen
import com.basicsandroid.newsapp.fragments.search.SearchScreen
import com.basicsandroid.newsapp.fragments.settings.SettingsScreen
import com.basicsandroid.newsapp.ui.theme.NewsAppTheme
import com.basicsandroid.newsapp.utils.NavigationDrawerSheet
import com.basicsandroid.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

       // enableEdgeToEdge()
        enableEdgeToEdge(
            statusBarStyle =SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
   OpenNewsScreen()
            }
        }
    }
    fun openWebSiteForNews(url:String){
   val uri= Uri.parse(url)
        val intent=Intent(Intent.ACTION_VIEW,uri)
        startActivity(intent)

    }
}
@Composable
fun OpenNewsScreen(){
    val navController= rememberNavController()
    NewsScreenContent( onSearchClick={
        navController.navigate("search")
    })

}

@Composable
fun NewsScreenContent( onSearchClick: () -> Unit ){
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope= rememberCoroutineScope()
    val navController= rememberNavController()
    val toolbarTitle= remember {
        mutableIntStateOf(R.string.news_app)
    }
    ModalNavigationDrawer(
      drawerState=drawerState,
      drawerContent = {

      NavigationDrawerSheet(onCategoriesClick = {
          navController.popBackStack()
      if(navController.currentDestination?.route!=CategoriesScreen().route)
              navController.navigate(CategoriesScreen().route)
          scope.launch {
              drawerState.close()
          }

      }, onSettingsClick = {
          scope.launch {
              navController.navigate("settings")
              drawerState.close()
          }

      })
  }) {
  Column (){
     NavHost(navController =navController ,
         startDestination = CategoriesScreen().route
     ){
         composable(CategoriesScreen().route){
             toolbarTitle.intValue=R.string.news_app
             CategoriesScreen(viewModel(),navController)
         }

         composable("${NewsScreen().route}/{category_name}/{category_Id}",
             arguments = listOf(navArgument("category_Id"){
             type= NavType.StringType
         },
                 navArgument("category_name"){
                     type=NavType.IntType
                 }
             )){ navBackStackEntry->
             val category=navBackStackEntry.arguments?.getString("category_Id")
             val categoryName=navBackStackEntry.arguments?.getInt("category_name")
             toolbarTitle.intValue=categoryName?:R.string.news_app
             
              NewsScreen(
                  category = category?:"",
                  navHostController = navController,
                  onNewsClick = {
                      title->
                      navController.navigate("newsDetails/${title}")
                  },
                 )

         }

         composable(route="newsDetails/{title}" ,arguments= listOf(navArgument("title"){
             type= NavType.StringType
         })){navBackStackEntry->
             val title=navBackStackEntry.arguments?.getString("title")?:""
NewsDetailsScreen(title = title, scope =scope , drawerState =drawerState )

         }
         composable(route = "search") {
             SearchScreen { title ->
                 navController.navigate("newsDetails/$title")
             }
         }

         composable(route="settings"){
             SettingsScreen(scope=scope,drawerState=drawerState)
         }

     }
  }
  }


}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun NewsCardPreview(){
NewsScreenContent {

}

}


//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun NewsListPreview(){
//NewsList()
//}



