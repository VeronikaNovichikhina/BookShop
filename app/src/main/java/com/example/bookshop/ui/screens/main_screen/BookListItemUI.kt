package com.example.bookshop.ui.screens.main_screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bookshop.data.Book
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookListItemUI(
    showEditButton: Boolean = false,
    book : Book,
    onEditClick: (Book) -> Unit,
    onFavoriteClick: () -> Unit,
    onBookClick: (Book) -> Unit,
    ) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onBookClick(book)
            }
    ) {
        var bitmap : Bitmap? = null
        try {
            val base64Image = android.util.Base64.decode(book.imageUrl, android.util.Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)
        } catch (e: IllegalArgumentException) {

        }
        AsyncImage(
            bitmap ?: book.imageUrl,
            "",
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
            text = book.year,
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = book.description,
            fontSize = 16.sp,
            color = Color.Gray,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.fillMaxWidth().weight(1f),
                text = book.price,
                fontSize = 18.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
            if(showEditButton)
                IconButton(onClick = {
                    onEditClick(book)
            }) {
                Icon(Icons.Filled.Edit,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                onFavoriteClick()
            }) {
                Icon(
                    if(book.isFavorite){
                        Icons.Default.Favorite
                    }else{
                        Icons.Filled.FavoriteBorder
                         },
                    contentDescription = null
                )
            }
        }
    }
}
