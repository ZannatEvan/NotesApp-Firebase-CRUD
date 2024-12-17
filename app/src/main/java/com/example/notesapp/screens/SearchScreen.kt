package com.example.notesapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notesapp.customtextfield.CustomTextField
import com.example.notesapp.model.Notes
import com.example.notesapp.model.ShareViewModel
import com.example.notesapp.navigation.Navigation
import com.example.notesapp.ui.theme.whiteColor
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SearchScreen(
    viewModel: ShareViewModel,
    navHostController: NavHostController,

) {
    val filterNotes = remember { mutableStateListOf<Notes>() }
    val searchQuery = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(searchQuery.value) {
        focusRequester.requestFocus()
        viewModel.setSearchQuery(searchQuery.value)
        filterNotes.clear()
        filterNotes.addAll(viewModel.getFilteredNotes())
    }
    Scaffold (
        containerColor = whiteColor
    ){ innerPadding->
        Box(modifier = Modifier
            .padding(innerPadding)
            .padding(10.dp)){
            Column {
                CustomTextField(
                    onValueChange ={it ->
                        searchQuery.value = it

                                   },
                    value = searchQuery.value,
                    modifier = Modifier.padding(),
                    line = 1,
                    enble = true,
                    searchClick ={},
                    height = 56.dp,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    },
                    placeHolder = { Text("Search notes..") },
                    textsyle = TextStyle(),
                    focusRequest = focusRequester
                )
                Spacer(Modifier.height(10.dp))
                if (filterNotes.isEmpty()) {
                    Text("Notes is not found !")
                }else{
                    LazyColumn {
                            items(filterNotes){note->


                                NotesListItem(note,
                                    FirebaseFirestore.getInstance()
                                        .collection("newnotes"),
                                    navHostController =navHostController)
                                }
                            }
                    }
                }

            }
        }
    }

