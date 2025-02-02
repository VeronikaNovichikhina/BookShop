package com.example.bookshop.ui.screens.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookshop.ui.theme.ButtonColor

@Composable
fun RoundedTextField(
    maxLine: Int = 1,
    singleLine : Boolean = true,
    text: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = label, color = Color.Gray)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .border(1.dp, ButtonColor, RoundedCornerShape(30)),
        shape = RoundedCornerShape(30),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        singleLine = singleLine,
        maxLines = maxLine
    )
}