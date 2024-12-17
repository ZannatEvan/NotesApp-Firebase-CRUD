package com.example.notesapp.customtextfield

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.theme.floatingButtonColor
import com.example.notesapp.ui.theme.whiteColor

@Composable
fun CustomFloatingActionButton(modifier: Modifier = Modifier,
                               floatingClickAction : ()->Unit,
                               content : @Composable ()->Unit
) {
    FloatingActionButton(
        modifier = Modifier.clip(shape = RoundedCornerShape(40.dp)),
        onClick = {floatingClickAction()},
        content = content,
        elevation = FloatingActionButtonDefaults.elevation(15.dp),
        containerColor = floatingButtonColor

    )
}

