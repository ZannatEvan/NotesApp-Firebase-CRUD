package com.example.notesapp.customtextfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.notesapp.ui.theme.whiteColor
import kotlin.math.absoluteValue


@Composable
fun CustomTextField(
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier,
    leadingIcon: @Composable () -> Unit,
    line : Int,
    enble : Boolean,
    searchClick: ()->Unit,
    height : Dp,
    leadingICon : @Composable (()-> Unit)?=null,
    placeHolder : @Composable ()->Unit,
    textsyle : TextStyle,
    focusRequest : FocusRequester
) {
    val interactionSource = remember { MutableInteractionSource() }
    val baseHeight = 56.dp
    val scale =height / baseHeight

    Box (modifier=Modifier.clickable(indication = null, onClick = searchClick,
        interactionSource = interactionSource).background(color = whiteColor)){
        TextField(

            textStyle = textsyle,
            maxLines = line,
            modifier = Modifier
                .background(color = Color.White)
                .focusRequester(focusRequest)
                .fillMaxWidth(1f)
                .height(height)
                .border(
                    border = BorderStroke(1.dp , color = Color.Gray),
                    shape = RoundedCornerShape(10.dp)
                ),
            onValueChange = onValueChange,
            value = value,
           placeholder = placeHolder,
            leadingIcon =if (leadingICon != null) leadingICon else null,
            enabled = enble,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = whiteColor,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,

                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = whiteColor,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
            )

        )
    }


}