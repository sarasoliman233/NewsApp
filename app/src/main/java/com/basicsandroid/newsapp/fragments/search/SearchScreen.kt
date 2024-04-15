package com.basicsandroid.newsapp.fragments.search

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.fragments.news.NewsList
import com.basicsandroid.newsapp.ui.theme.NewsAppTheme
import com.basicsandroid.newsapp.ui.theme.green


@Composable
fun SearchScreen(vm:SearchViewModel= viewModel(), onNewsClick:(String)->Unit){

    val focusManager= LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .paint(painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.1f)
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .background(green)
            .padding(horizontal = 20.dp), contentAlignment = Alignment.Center
        ){
            TextField(value =vm.searchQuery ,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent

),
                onValueChange = {vm.searchQuery=it},
                placeholder ={ Text(text = "Search Articles")},
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.search_icon), contentDescription ="", modifier =
                    Modifier
                        .size(17.dp)
                        .clickable {
                            vm.searchArticles()
                            focusManager.clearFocus()
                        },

                )


    },
                maxLines = 1, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    vm.searchArticles()
                    focusManager.clearFocus()
                }
                   ),
                trailingIcon = {
                    if(vm.isFocused){
                         Image(painter = painterResource(id = R.drawable.close_icon), contentDescription = "close", modifier =
                         Modifier
                             .size(17.dp)
                             .clickable {
                                 if (vm.searchQuery.isNotEmpty()) {
                                     vm.searchQuery = ""
                                 } else {
                                     focusManager.clearFocus()
                                     vm.isFocused = false
                                 }
                             })

                    }
                },
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(22.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(vm.focusRequester)
                    .onFocusChanged { vm.isFocused = true })
        }
        NewsList(newsList =vm.newsList,

             onNewsClick = {onNewsClick(it)})
        
        DisposableEffect(key1 = vm.searchQuery ){
            onDispose { vm.getSearchArticlesCall?.cancel() }
        }

    }}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SearchScreenPreview(){
    SearchScreen(onNewsClick = {})
}
