package com.example.bookshop.ui.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.bookshop.data.Book
import com.example.bookshop.ui.screens.login.data.MainScreenDataObject
import com.example.bookshop.ui.screens.main_screen.bottom_menu.BottomMenu
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    onAdminClick: () -> Unit
){

    val drawerState = rememberDrawerState(DrawerValue.Open)
    val coroutineScope = rememberCoroutineScope()

    val booksListState = remember {
        mutableStateOf(emptyList<Book>())
    }
    LaunchedEffect(Unit) {
        val db  = Firebase.firestore
        getAllBooks(db){ books ->
            booksListState.value = books
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                Modifier.fillMaxWidth(0.7f)
            ) {
                DrawerHeader(navData.email)
                DrawerBody{
                    coroutineScope.launch{
                        drawerState.close()
                    }
                    onAdminClose()
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu() }
        ){
            LazyVerticalGrid(GridCells.Fixed(2)) {
                items(booksListState.value){book->
                    BookListItemUI(book)
                }
            }
        }
    }
}
private fun getAllBooks(
    db: FirebaseFirestore,
    onBooks: (List<Book>)-> Unit
) {
    db.collection("books")
        .get()
        .addOnSuccessListener {task->
            onBooks(task.toObjects(Book::class.java))
        }

}