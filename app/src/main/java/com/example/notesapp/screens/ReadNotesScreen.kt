@file:Suppress("UNUSED_EXPRESSION")

package com.example.notesapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.theme.floatingButtonColor
import com.example.notesapp.ui.theme.whiteColor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

@SuppressLint("SuspiciousIndentation")
@Composable
fun ReadNotesScreen(
    navHostController: NavHostController,
    title: String,
    description1: String,
    id: String,
) {
    val scrollState = rememberScrollState()
    val db = FirebaseFirestore.getInstance()
    val noteDb = db.collection("newnotes")
//    val title = remember { mutableStateOf("") }
//    val descrip = remember { mutableStateOf("") }
//    LaunchedEffect(Unit) {
//        if (id != "defaultId"){
//            noteDb.document(id).get().addOnSuccessListener {it->
//                val data = it.toObject(Notes::class.java)
//             title.value    = data?.title.toString()
//                descrip.value = data?.description.toString()
//
//            }
//        }
//    }
            Scaffold (containerColor = whiteColor,
                bottomBar = {  Button(onClick = {
                    navHostController.popBackStack()
                }, modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp, horizontal = 15.dp), colors = ButtonColors(
                    containerColor = floatingButtonColor,
                    contentColor = whiteColor,
                    disabledContainerColor = floatingButtonColor,
                    disabledContentColor = floatingButtonColor
                )){ Text("Back") } }){ innerPadding->
                Box(Modifier.padding(innerPadding).padding(10.dp)){
                    Column (modifier = Modifier.verticalScroll(scrollState)){
                        Text(description1, fontSize = 32.sp, fontWeight = FontWeight.Bold, overflow = TextOverflow.Visible)
                        Spacer(Modifier.height(20.dp))
                        Text(id, fontSize = 23.sp,overflow = TextOverflow.Visible)
                    }
                }

            }
}