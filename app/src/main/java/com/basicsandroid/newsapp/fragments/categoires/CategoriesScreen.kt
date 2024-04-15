package com.basicsandroid.newsapp.fragments.categoires

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.basicsandroid.newsapp.Model.Category
import com.basicsandroid.newsapp.Model.NewsScreen
import com.basicsandroid.newsapp.Model.api.Constants
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.ui.theme.black
import com.basicsandroid.newsapp.utils.NavigationDrawerSheet
import com.basicsandroid.newsapp.utils.NewsTopAppBar
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(vm:CategoriesViewModel=viewModel(),navController: NavHostController){
    val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope= rememberCoroutineScope()
    val toolbarTitle= remember {
        mutableIntStateOf(R.string.news_app)
    }
    ModalNavigationDrawer(
        drawerState=drawerState,
        drawerContent = {

            NavigationDrawerSheet(onCategoriesClick = {
                navController.popBackStack()
                if(navController.currentDestination?.route!= com.basicsandroid.newsapp.Model.CategoriesScreen().route)
                    navController.navigate(com.basicsandroid.newsapp.Model.CategoriesScreen().route)
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
            }) { paddingValues ->
            paddingValues
            Column(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.pattern),
                        contentScale = ContentScale.Crop
                    )

            ) {
                Text(
                    text = "Pick your category \n" + "of interest", fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    color = black, modifier = Modifier.padding(horizontal = 24.dp, vertical = 22.dp)
                )
                CategoriesList(vm, navController)


            }

        }


    }}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CategoriesFragmentPreview(){
    CategoriesScreen(viewModel(),rememberNavController())
}
@Composable
fun CategoriesList(vm:CategoriesViewModel,navController: NavHostController){
LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()){
    items(vm.categories.size) {position->
        CategoryCard(category =vm.categories.get(position),position,navController=navController)
    }
}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CategoriesListPreview(){
    CategoriesList(viewModel(),rememberNavController() )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Category,index:Int,navController:NavHostController){
Card (
    shape = if(index%2==0) RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp, bottomStart = 24.dp)
    else RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomEnd = 24.dp)
    , modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 16.dp)
        .height(160.dp), onClick = {
  navController.navigate(route = "${NewsScreen().route}/${category.titleResID}/${category.apiID}")
    },
    colors = CardDefaults.cardColors(containerColor = colorResource(id = category.backgroundColor))){
Image(painter = painterResource(id = category.drawableResId),
    contentDescription = stringResource(R.string.category_image), modifier = Modifier
        .fillMaxHeight(.7F)
        .fillMaxWidth(.7F)
        .padding(vertical = 5.dp, horizontal = 5.dp)
        .align(Alignment.CenterHorizontally), contentScale = ContentScale.Crop )
    Text(text = stringResource(id = category.titleResID), color = Color.White, modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 22.sp, fontFamily = FontFamily(Font(R.font.poppins)) )
}
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun CategoryCardPreview(){
    CategoryCard(Constants.categories.get(0),0, rememberNavController())
}