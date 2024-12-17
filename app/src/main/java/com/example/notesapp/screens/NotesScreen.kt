package com.example.notesapp.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.notesapp.customtextfield.CustomFloatingActionButton
import com.example.notesapp.customtextfield.CustomTextField
import com.example.notesapp.model.Encoderparameter
import com.example.notesapp.model.Notes
import com.example.notesapp.model.ShareViewModel
import com.example.notesapp.navigation.Navigation
import com.example.notesapp.ui.theme.whiteColor
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesScreen(navHostController: NavHostController,shareViewModel: ShareViewModel) {
    val context = LocalContext.current;
    val firebaseDb = FirebaseFirestore.getInstance()
    val noteDb =firebaseDb.collection("newnotes");
    val list = remember { mutableStateListOf<Notes>() }
    val loadingValue = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        noteDb.addSnapshotListener{ value,error ->
            if (error == null){
                val data = value?.toObjects(Notes::class.java)
                if (data != null){
                list.clear()
                list.addAll(data)
                    shareViewModel.setNoteList(data)
                }
                loadingValue.value = true

            }else{
                Toast.makeText(context,"data is not found",Toast.LENGTH_LONG).show()
                loadingValue.value =false
            }
        }
    }

    Scaffold (
        containerColor = whiteColor,
        floatingActionButton = {
            CustomFloatingActionButton(
               content = { Icon(imageVector = Icons.Default.Add, tint = whiteColor, contentDescription = "",  )},
                floatingClickAction = {
                    navHostController.navigate(Navigation.InsertNotes.route+"/defaultId")
                }
            )
        }
    ){innnerPadding->
      Box(
          modifier = Modifier
              .padding(innnerPadding)
              .padding(15.dp)
      ){
          Column {

          Row {
              Text(text = "Notes App",
                  fontSize = 35.sp,
                  fontWeight = FontWeight.Bold
              )
          }
              Spacer(Modifier.height(10.dp))
          Column {

              CustomTextField(
                  searchClick = {
                      navHostController.navigate(Navigation.SearchScreen.route)


                  },
                  onValueChange = {},
                  value = "",
                  modifier = Modifier
                      .background(color = Color.White)
                      .fillMaxWidth(1f)
                      .border(
                          border = BorderStroke(1.dp, color = Color.Gray),
                          shape = RoundedCornerShape(40.dp)
                      )
                      .clip(shape = RoundedCornerShape(40.dp)),
                  placeHolder = { Text("Search notes") },
                  leadingIcon = {
                      Icon(imageVector = Icons.Default.Search, contentDescription = "")
                  },
                  line = 1,
                  enble = false,
                  height = 52.dp,
                  textsyle = TextStyle(color = Color.Black),
                  focusRequest = FocusRequester.Default

              )
              Spacer(Modifier.height(10.dp))
              if (loadingValue.value){
                  if(list.isEmpty()){
                      Box(modifier = Modifier.fillMaxSize()){
                          Text("Notes is empty ", modifier = Modifier.align(alignment = Alignment.Center))
                      }
                  }else{

                      LazyColumn {
                          items(list){item->

                              NotesListItem(item,noteDb,navHostController)
                          }
                      }
                  }
              }else{
                  Box (modifier = Modifier.fillMaxSize()){

                  CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
                  }
              }

          }
          }

      }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListItem(notes: Notes, notesDb: CollectionReference, navHostController: NavHostController) {
    val expend = remember { mutableStateOf(false) }
    val dropdownOffset = remember{ mutableStateOf(DpOffset.Zero) }
    val menuWidth = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val dialog = remember { mutableStateOf(false) }
    if (dialog.value){
       AlertDialog(
           onDismissRequest = {dialog.value=false},
           title = {  Text("Are you sure", color = Color.Black) },
           text = { Text("Do you wan to delete ?", color = Color.Black)},
           confirmButton = {
               Button(
                   onClick = {
               notesDb.document(notes.id.toString()).delete()
               dialog.value = false;
                   },
               ) {

                   Text("Yes", color = whiteColor)
               }
           },
           dismissButton = {
               Button(
                   onClick = {

               dialog.value = false
                   },
               ) { Text("No", color = whiteColor) }
           }
       )
    }else{

    }
   Card(
       elevation = CardDefaults.elevatedCardElevation(10.dp),
       modifier = Modifier
           .fillMaxWidth()
           .padding(vertical = 6.dp)
           .background(color = whiteColor)
           .clickable {
               navHostController?.navigate(
                   Navigation.ReadNotes.createRoute(
                       id = notes.id!!,
                       title = Encoderparameter(notes.title!!),
                       description = Encoderparameter(notes.description!!)
                   )
               )
               Log.d("TAG", "NotesListItem: " )
           },
       colors = CardColors(
           containerColor = whiteColor,
           contentColor = Color.Black,
           disabledContainerColor = Color.Transparent,
           disabledContentColor = Color.Transparent,
       ),


   ) {

       Box(modifier = Modifier
           .padding(15.dp)
           .fillMaxWidth()){
           DropdownMenu(
               properties = PopupProperties(focusable = true),
               onDismissRequest = {
                   expend.value = false
               },
               scrollState = rememberScrollState(),
               offset = dropdownOffset.value,
               expanded = expend.value,
               modifier = Modifier
                   .padding(1.dp)
                   .background(color = whiteColor)
                   .align(alignment = Alignment.TopEnd),
           ) {
               DropdownMenuItem(
                   text = { Text("Update") },
                   onClick = {
                       navHostController?.navigate(Navigation.InsertNotes.route + "/${notes.id}")
                   },
               )
               DropdownMenuItem(text = { Text("Delete") }, onClick = {
                    dialog.value= true

               },)
           }
           Icon(imageVector = Icons.Default.MoreVert,
               contentDescription = "",
               modifier = Modifier
                   .align(alignment = Alignment.TopEnd)
                   .clickable {
                       expend.value = true
                   }
                   .onGloballyPositioned { coordinate ->
                       menuWidth.value = coordinate.size.width
                       val xoffset = coordinate.positionInWindow().x.dp - menuWidth.value.dp
                       dropdownOffset.value = DpOffset(xoffset, 0.dp)
                   }
           )
        Column {
        Text(notes.title.toString(),
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(end = 10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Text(notes.description.toString(),
            color = Color.Black,
            fontSize = 17.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis)
            Text(notes.time.toString(),
                color = Color.Black)
        }
       }
   }
}
