package com.example.notesapp.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Encoderparameter(value : String) : String {
    return URLEncoder.encode(value,StandardCharsets.UTF_8.toString())
}