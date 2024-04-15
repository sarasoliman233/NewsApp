package com.basicsandroid.newsapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basicsandroid.newsapp.Model.NewsItem
import com.basicsandroid.newsapp.Model.api.ArticlesItem
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.ui.theme.grey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(model:ArticlesItem,onNewsClick:((String)->Unit)?=null){
    Card (modifier = Modifier.padding(16.dp).
    clickable {
        if (onNewsClick!=null) {
            onNewsClick(model.title?: "")
        }

    },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)){
        GlideImage(
            model = model.urlToImage?:"",
            contentDescription = stringResource(R.string.news_card_image),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.3F),
            contentScale = ContentScale.Fit,
            loading = placeholder(R.drawable.loaddd)

        )
        Text(text = model.source?.name?: "", Modifier.padding( vertical = 8.dp), color = grey, fontSize = 10.sp)
        Text(text = model.title?: "",
            Modifier.padding(vertical = 3.dp), fontSize = 14.sp, fontFamily = FontFamily(
            Font(R.font.poppins)
        )
        )
        Text(text = model.publishedAt?: "",
            Modifier.padding(horizontal = 3.dp).align(Alignment.End), color = grey, fontSize = 13.sp)
    }
}