package com.example.bookshop.ui.screens.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshop.ui.theme.ButtonColor

@Composable
fun LogInButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth().padding(top=12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(30),
        onClick = {
            onClick()
        },
    ) {
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            text = text
        )
    }
}