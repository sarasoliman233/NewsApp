package com.basicsandroid.newsapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.basicsandroid.newsapp.ui.theme.NewsAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
 enableEdgeToEdge(
     statusBarStyle =SystemBarStyle.light(
         Color.TRANSPARENT,Color.TRANSPARENT
     ),
     navigationBarStyle = SystemBarStyle.light(
         Color.TRANSPARENT,Color.TRANSPARENT
     )
 )
        super.onCreate(savedInstanceState)

        setContent {

            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Handler(Looper.getMainLooper()).postDelayed({

                        val intent=Intent(this@SplashActivity , NewsActivity::class.java)
                     startActivity(intent)
                    finish()
                },2500)
        SplashContent()
            }
        }
    }
}
@Composable
fun SplashContent(){
    Column(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.pattern),
            contentScale = ContentScale.FillBounds
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(2F))
        Image(painter = painterResource(id = R.drawable.logo) ,
            contentDescription ="App Logo Image",
            modifier = Modifier.fillMaxHeight(0.3F),
            contentScale=ContentScale.Crop
            )
        Spacer(modifier = Modifier.weight(1F))
        Image(painter = painterResource(id = R.drawable.route),
            contentDescription ="App Signature" ,
            modifier = Modifier.fillMaxHeight(0.37F),

            )


    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashPreview(){
    SplashContent()
}
