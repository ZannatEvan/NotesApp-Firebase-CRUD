package com.example.notesapp.screens

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.notesapp.navigation.Navigation
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreenPreview(){
    val controller = rememberNavController()
    SplashScreen(navHostController = controller)
}
@Composable
fun SplashScreen(navHostController: NavHostController) {

    Scaffold {innerpadding->
        Box(modifier = Modifier.padding(innerpadding).fillMaxSize().background(color = Color.White)){
            Column(modifier = Modifier.align(alignment = Alignment.Center)) {
              AsyncImage(model = "https://firebase.google.com/static/images/brand-guidelines/logo-logomark.png", contentDescription = "")
                CircularProgressIndicator()

            }
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navHostController.navigate(Navigation.HomeScreen.route){
           popUpTo(Navigation.SplashScreen.route){
               inclusive=true
           }
        }
    }
}