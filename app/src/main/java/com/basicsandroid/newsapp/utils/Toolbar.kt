package com.basicsandroid.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.ui.theme.green


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(titleResourceId:Int,titleString:String?=null,shouldDisplaySearchIcon:Boolean, shouldDisplayMenuIcon:Boolean,onSearchClick:()->Unit,onSideMenuClick:()->Unit){
    val navController= rememberNavController()

    TopAppBar(
        navigationIcon = { Image(painter = painterResource(id = R.drawable.ic_menu ),
            contentDescription = stringResource(R.string.navigation_drawer_icon),

            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable {
                    onSideMenuClick()
                })
        },
        title = {
            Text(text = stringResource(titleResourceId), modifier = Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.poppins)),
                textAlign = TextAlign.Center)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = green,
            titleContentColor = Color.White),
        modifier = Modifier.clip(
            RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 30.dp,
            bottomEnd = 30.dp
        )
        ),
        actions = {
            Image(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(
                    R.string.icon_search
                ),
                modifier = Modifier.padding(8.dp)
                    .size(25.dp)
                    .clickable {
                    onSearchClick()
                    }
            )
        }
    )
}


