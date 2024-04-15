package com.basicsandroid.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.ui.theme.black
import com.basicsandroid.newsapp.ui.theme.green

@Composable
fun NavigationDrawerSheet(onSettingsClick:()->Unit,onCategoriesClick:()->Unit){
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
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = Color.White
            )

        }
        NavigationDrawerItem(iconResId = R.drawable.ic_categ,
            textResId = R.string.categories ,
            onNavigationItemClick = {
                onCategoriesClick()
            })
        NavigationDrawerItem(iconResId = R.drawable.ic_setting, textResId = R.string.settings ,
            onNavigationItemClick = {
                onSettingsClick()
            })

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NavigationDrawerSheetPreview(){
    NavigationDrawerSheet(onSettingsClick = {}, onCategoriesClick = {})
}

@Composable
fun NavigationDrawerItem(iconResId:Int , textResId:Int,onNavigationItemClick:()->Unit){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.
        padding(8.dp)
        .clickable {
            onNavigationItemClick()
        }) {
        Image(painter = painterResource(id = iconResId),
            contentDescription = stringResource(R.string.navigation_item_icon),
            Modifier.size(20.dp)

        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = textResId), fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.poppins)), color = black)




    }
}