package com.example.notesapp.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesapp.customtextfield.CustomFloatingActionButton
import com.example.notesapp.customtextfield.CustomTextField
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.theme.whiteColor
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("WeekBasedYear")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateNotesScreen(navHostController: NavHostController, id: String?) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val firebaseInstance = FirebaseFirestore.getInstance()
    val notesDb = firebaseInstance.collection("newnotes")
    val localtime = remember {
        LocalDateTime.now()
    }
    val formate = DateTimeFormatter.ofPattern("MMM d,h:mm a")
    val curTime = localtime.format(formate)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (id != "defaultId") {
            notesDb.document(id.toString()).get().addOnSuccessListener { it->
                val data = it.toObject(Notes::class.java)
                title.value = data?.title.toString()
                description.value = data?.description.toString()
            }
        }else{
            Toast.makeText(context,"id is empty $id",Toast.LENGTH_LONG).show()

        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = whiteColor,
        floatingActionButton = {
            CustomFloatingActionButton (
                content = {
                    Icon(imageVector = Icons.Default.Done, tint = whiteColor, contentDescription = "",  )
                },
                floatingClickAction = {
                    if (title.value.isNotEmpty() && description.value.isNotEmpty()){

                    val id = if (id != "defaultId"){
                        id.toString()
                    }else{
                        notesDb.document().id
                    }
                    val notes = Notes(
                        title = title.value,
                        description = description.value,
                        time = curTime,
                        id = id
                    )
                    notesDb.document(id).set(notes).addOnCompleteListener { it->
                        if (it.isSuccessful){
                            Toast.makeText(context,"successfully added",Toast.LENGTH_LONG).show()
                            navHostController.popBackStack()
                        }else{
                            Toast.makeText(context,"Added failed something wrong ",Toast.LENGTH_LONG).show()
                        }
                    }
                    }else{
                        Toast.makeText(context,"Field is empty",Toast.LENGTH_LONG).show()

                    }
                },
                modifier = Modifier.padding()
            )
        }
    ) { innerPadding->
        Box(
            Modifier
                .padding(innerPadding)
                .padding(15.dp)){
            Column {
            Text("Create Notes", fontSize = 35.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))
                CustomTextField(
                   placeHolder = { Text("Title", fontSize = 25.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    searchClick = {},
                    onValueChange = {it->
                        title.value = it
                                    },
                    value = title.value,
                    leadingIcon = {},
                    line = 4,
                    enble = true,
                    height = 100.dp,
                    textsyle = TextStyle(fontSize = 25.sp),
                    focusRequest = FocusRequester.Default,
                )
                Spacer(Modifier.height(20.dp))
                CustomTextField(
                   placeHolder = { Text("Description") },
                    searchClick = {},
                    onValueChange = {it->
                        description.value = it
                                    },
                    value = description.value,
                    leadingIcon = {},
                    line = 200,
                    enble = true,
                    height = 500.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                        .fillMaxHeight(1f),
                    textsyle = TextStyle(fontSize = 20.sp),
                    focusRequest = FocusRequester.Default,

                )
            }
        }

    }

}