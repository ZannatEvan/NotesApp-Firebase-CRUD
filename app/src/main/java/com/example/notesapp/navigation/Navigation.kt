package com.example.notesapp.navigation

import com.example.notesapp.model.Encoderparameter


sealed class Navigation (val route : String){
    data object SplashScreen : Navigation(route = "splash")
    data object HomeScreen : Navigation(route = "home")
    data object InsertNotes : Navigation(route = "create_notes")
    data object SearchScreen : Navigation(route = "search_screen")
    data object ReadNotes : Navigation(route = "read_notes/{id}/{title}/{description}"){
        fun createRoute(id : String,title  : String,description : String):String{
            return "read_notes/$id/${Encoderparameter(title)}/${Encoderparameter(description)}"
        }
    }

}