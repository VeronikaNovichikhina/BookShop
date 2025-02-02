package com.example.bookshop.ui.add_book_screen


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.bookshop.ui.screens.login.LogInButton
import com.example.bookshop.ui.screens.login.RoundedTextField
import com.example.bookshop.ui.theme.Transparent

@Composable
fun AddBookScreen() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var price by remember { mutableStateOf("") }

    var selectedImaheUrl by remember { mutableStateOf<Uri?>(null) }

    val imagelauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
    ){uri->
        selectedImaheUrl = uri
    }
    Image(
        painter = rememberAsyncImagePainter(
            model = selectedImaheUrl),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4F
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Spacer(Modifier.height(15.dp))
            Text(
                "Add new Book",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, start = 10.dp, end = 50.dp),
                letterSpacing = 1.sp,
                lineHeight = 40.sp
            )
            Spacer(Modifier.height(15.dp))
            RoundedCornerDropDownMenu { selectedItem ->

            }
            Spacer(Modifier.height(15.dp))
            RoundedTextField(
                text = title,
                onValueChange = {
                    title = it
                },
                label = "Title"
            )
            RoundedTextField(
                text = description,
                onValueChange ={
                    description = it
                },
                label = "Description",
                singleLine = false,
                maxLine = 4
            )
            RoundedTextField(
                text = price,
                onValueChange ={
                    price = it
                },
                label = "Price"
            )
            LogInButton(
                text = "Select Image"
            ) {
                imagelauncher.launch("image/*")
            }
            LogInButton(
                text = "Save"
            ) {

            }
        }
    }
}
private fun saveBookImage(uri: Uri){
    
}