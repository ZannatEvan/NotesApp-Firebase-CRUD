package com.example.notesapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notesapp.model.ShareViewModel
import com.example.notesapp.screens.CreateNotesScreen
import com.example.notesapp.screens.NotesScreen
import com.example.notesapp.screens.ReadNotesScreen
import com.example.notesapp.screens.SearchScreen
import com.example.notesapp.screens.SplashScreen

@SuppressLint("NewApi")
@Composable
fun NotesNavigation(navHostController: NavHostController) {
    val shareViewModel = remember { ShareViewModel() }
    NavHost(navController = navHostController, startDestination = Navigation.SplashScreen.route) {
        composable(Navigation.SplashScreen.route){
            SplashScreen(navHostController)
        }
        composable(Navigation.HomeScreen.route){
            NotesScreen(
                navHostController,
                shareViewModel
            )
        }

        composable(Navigation.SearchScreen.route,

        ) {it->

            SearchScreen(shareViewModel,navHostController)
        }
        composable(Navigation.InsertNotes.route+"/{id}"){it->
            val id  = it.arguments?.getString("id")
            CreateNotesScreen(navHostController,id)
        }
        composable(Navigation.ReadNotes.route,
             arguments = listOf(
                 navArgument("id"){type= NavType.StringType},
                 navArgument("title"){type= NavType.StringType},
                 navArgument("description"){type= NavType.StringType}

             )
            ) {backStackEntry->
            val id = backStackEntry.arguments?.getString("id")?:""
            val title = backStackEntry.arguments?.getString("title")?:""
            val description = backStackEntry.arguments?.getString("description")?:""
            ReadNotesScreen(navHostController,id,title,description)
        }
    }
}

