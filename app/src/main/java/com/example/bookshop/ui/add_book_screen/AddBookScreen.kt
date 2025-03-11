package com.example.bookshop.ui.add_book_screen


import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.bookshop.data.Book
import com.example.bookshop.ui.screens.login.LogInButton
import com.example.bookshop.ui.screens.login.RoundedTextField
import com.example.bookshop.ui.theme.Transparent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

@Composable
fun AddBookScreen(
    navData: AddScreenObject = AddScreenObject(),
    onSaved: () -> Unit ={}
) {
    val cv = LocalContext.current.contentResolver

    var selectedCategory  by remember { mutableStateOf(navData.category) }
    var title by remember { mutableStateOf(navData.title) }
    var description by remember { mutableStateOf(navData.description) }
    var price by remember { mutableStateOf(navData.price) }
    var selectedImageUrl by remember { mutableStateOf<Uri?>(null) }

    val imagBitMap = remember {
        var bitmap : Bitmap? = null
        try {
            val base64Image = android.util.Base64.decode(navData.imageUrl, android.util.Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)
        } catch (e: IllegalArgumentException) {

        }
        mutableStateOf(bitmap)
    }
    val firestore = remember { Firebase.firestore }

    val imagelauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
    ){uri->
        imagBitMap.value = null
        selectedImageUrl = uri
    }
    Image(
        painter = rememberAsyncImagePainter(
            model = imagBitMap.value ?: selectedImageUrl
        ),
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
            RoundedCornerDropDownMenu(selectedCategory) { selectedItem ->
                selectedCategory = selectedItem
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
                saveBookToFireStore(
                    firestore,
                    Book(
                        key = navData.key,
                        imageUrl = if(selectedImageUrl != null)
                            imageBase64(
                            selectedImageUrl!!,
                            cv
                        )else navData.imageUrl,
                        title = title,
                        description = description,
                        price = price,
                        category = selectedCategory,
                    ),
                    onSaved = onSaved,
                    onError = {

                    }
                )
            }
        }
    }
}
private  fun imageBase64(
    uri: Uri,
    contentResolver: ContentResolver
): String{
    val inputStream = contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes()
    return bytes?.let{
        android.util.Base64.encodeToString(it, android.util.Base64.DEFAULT)
        }?: ""
}

private fun saveBookToFireStore(
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
){
    val db = firestore.collection("books")
    val key = book.key.ifEmpty {db.document().id}
    db.document(key)
        .set(
            book.copy(
               key = key,
            )
        )
        .addOnSuccessListener {
            onSaved()
        }
        .addOnFailureListener {
            onError()
        }
}