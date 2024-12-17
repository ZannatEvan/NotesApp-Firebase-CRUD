package com.example.notesapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    private val _notesList = mutableStateOf<List<Notes>>(emptyList())
     private val noteList : State<List<Notes>> = _notesList

    private val _searchQuery = mutableStateOf("")
     private val searchQuery  : State<String> = _searchQuery

    fun setNoteList(notes :List<Notes>){
        _notesList.value = notes
    }

    fun setSearchQuery(query : String){
        _searchQuery.value = query
    }

    fun  getFilteredNotes() : List<Notes>{
        return noteList.value.filter { it->
            it.title?.contains(searchQuery.value, ignoreCase = true) ?: true
        }
    }
}