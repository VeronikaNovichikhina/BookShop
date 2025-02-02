package com.example.bookshop.ui.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bookshop.data.Book

@Composable
fun BookListItemUI(book : Book) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            book.imageUrl,
            null,
            modifier = Modifier.fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = book.title,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = book.description,
            fontSize = 16.sp,
            color = Color.Gray,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = book.price,
            fontSize = 18.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
    }
}