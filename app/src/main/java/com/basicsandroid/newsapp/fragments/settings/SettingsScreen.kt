package com.basicsandroid.newsapp.fragments.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.basicsandroid.newsapp.NewsActivity
import com.basicsandroid.newsapp.R
import com.basicsandroid.newsapp.ui.theme.black
import com.basicsandroid.newsapp.ui.theme.green
import kotlinx.coroutines.CoroutineScope
import java.nio.file.WatchEvent.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(scope: CoroutineScope,drawerState: DrawerState){

    Scaffold(  topBar={

      SettingsTobBar {

      }
    }  )

    { paddingValues ->
        paddingValues
        Column(modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .paint(
                painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop
            )
            .padding(30.dp)) 
        {
            Text(text = stringResource(id = R.string.language),
                androidx.compose.ui.Modifier.padding(vertical = 10.dp),
                fontSize = 20.sp,
                color = black,
                fontFamily = FontFamily(Font(R.font.poppins_bold))
            )

            Spacer(modifier = androidx.compose.ui.Modifier.height(10.dp) )


            var isExpanded by rememberSaveable {
                mutableStateOf(false)
            }
            val systemLanguage= when(Locale.current.language){
                    "en"->"English"
                "ar"->"العربية"

                else -> {"English"}
            }
            val currentLanguage=AppCompatDelegate.getApplicationLocales()[0]?.displayLanguage?:systemLanguage
            var selectedLanguage by rememberSaveable {
                mutableStateOf<String>(currentLanguage)
            }
            val activity= (LocalContext.current) as NewsActivity
            ExposedDropdownMenuBox(expanded =isExpanded , onExpandedChange = { isExpanded = it },
                modifier = androidx.compose.ui.Modifier.fillMaxSize()) {

                OutlinedTextField(value = selectedLanguage,
                    onValueChange ={

                }
                    , readOnly = true
                    , trailingIcon ={
               ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
                    , colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = green, unfocusedBorderColor = black, focusedTextColor = green, unfocusedTextColor = black, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                    modifier = androidx.compose.ui.Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded=false }) {
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.english))},
                        onClick = { 
                            selectedLanguage="English"
                            isExpanded=false
                            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                            activity.finish()
                            activity.startActivity(activity.intent)

                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.arabic))},
                        onClick = {
                           selectedLanguage="العربية"
                            isExpanded=false
                            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                               activity.finish()
                            activity.startActivity(activity.intent)

                        },contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                }
                
            }

        }

    }

}



@Composable
@Preview
fun PreviewSettingsScreen(){
    SettingsScreen(rememberCoroutineScope(), rememberDrawerState(initialValue =DrawerValue.Closed ) )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTobBar(onSideMenuClick:()->Unit){
    TopAppBar(
        navigationIcon = { Image(painter = painterResource(id = R.drawable.ic_menu ),
            contentDescription = stringResource(R.string.navigation_drawer_icon),
            modifier = androidx.compose.ui.Modifier
                .padding(8.dp)
                .clickable {
                    onSideMenuClick()
                })
        },
        title = {
            Text(text = stringResource(R.string.settings_topBar),
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                fontFamily = FontFamily(Font(R.font.poppins)),
                textAlign = TextAlign.Center)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = green,
            titleContentColor = Color.White),
        modifier = androidx.compose.ui.Modifier.clip(
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 30.dp,
                bottomEnd = 30.dp
            )
        )
    )
}