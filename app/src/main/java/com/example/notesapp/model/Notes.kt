package com.example.notesapp.model

data class Notes(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val time: String? = null
){
    constructor():this(null,null,null,null)
}
