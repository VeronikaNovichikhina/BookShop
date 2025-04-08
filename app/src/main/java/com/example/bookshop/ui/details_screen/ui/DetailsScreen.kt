package com.example.bookshop.ui.details_screen.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bookshop.R
import com.example.bookshop.ui.details_screen.data.DetailsNavObject

@Composable
fun DetailsScreen(
    navObject : DetailsNavObject = DetailsNavObject()
) {
    var bitmap : Bitmap? = null
    try {
        val base64Image = android.util.Base64.decode(navObject.imageUrl, android.util.Base64.DEFAULT)
        bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)
    } catch (e: IllegalArgumentException) {

    }
    Column(modifier = Modifier.fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween)
    {
        Column(modifier = Modifier.fillMaxWidth())
        {
            Row(modifier = Modifier.fillMaxWidth())
            {
                AsyncImage(
                    bitmap ?: navObject.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.5f)
                        .height(190.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.FillHeight
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.fillMaxWidth()
                    .height(190.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text ="Категория:",
                        color = Color.Gray)
                    Text(text =navObject.category,
                        color = Color.Gray)
                    Text(text ="Автор:",
                        color = Color.Gray)
                    Text(text ="J. R. R. Tolkien",
                        fontWeight = FontWeight.Bold)
                    Text(text ="Дата печати: ",
                        color = Color.Gray)
                    Text(text ="16-02-1954",
                        fontWeight = FontWeight.Bold)
                    Text(text ="Оценка:",
                        color = Color.Gray)
                    Text(text ="4.8",
                        fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = navObject.title,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(text = navObject.description,
                fontSize = 16.sp
            )
        }
       Button(
           modifier = Modifier.fillMaxWidth(),
           onClick = {

       }) {
           Text(text = "${navObject.price} Buy Now")
       }
    }
}