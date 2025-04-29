package com.example.bookshop.ui.screens.main_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookshop.data.Book
import com.example.bookshop.data.Favorite
import com.example.bookshop.ui.screens.login.LogInButton
import com.example.bookshop.ui.screens.login.RoundedTextField
import com.example.bookshop.ui.screens.login.data.LoginScreenObject
import com.example.bookshop.ui.screens.login.data.MainScreenDataObject
import com.example.bookshop.ui.screens.main_screen.bottom_menu.BottomMenu
import com.example.bookshop.ui.screens.main_screen.bottom_menu.BottomMenuItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navData: MainScreenDataObject,
    onLogOut:()->Unit,
    onBookEditClick: (Book) -> Unit,
    onBookClick: (Book) -> Unit,
    onAdminClick: () -> Unit,

){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val booksListState = remember {
        mutableStateOf(emptyList<Book>())
    }
    val selectedBottomItemState = remember {
        mutableStateOf(BottomMenuItem.Home.title)
    }
    val isAdminState = remember {
        mutableStateOf(false)
    }
    val isFavListEmptyState = remember {
        mutableStateOf(false)
    }
    val db  = remember { Firebase.firestore}
    LaunchedEffect(Unit) {
        getAllFavs(db, navData.uid){favs->
            getAllBooks(db, favs){ books->
                isFavListEmptyState.value = books.isEmpty()
                booksListState.value = books
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                Modifier.fillMaxWidth(0.7f)
            ) {
                DrawerHeader()
                DrawerBody(
                    onAdmin = {isAdmin->
                        isAdminState.value = isAdmin
                    },
                    onFavClick = {
                        selectedBottomItemState.value = BottomMenuItem.Favs.title
                        getAllFavs(db, navData.uid){favs->
                            getAllFavsBook(db, favs){books->
                                isFavListEmptyState.value = books.isEmpty()
                                booksListState.value = books
                            }
                        }
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                    onAdminClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        onAdminClick()
                    },
                    onCategoryClick = { category->
                        getAllFavs(db, navData.uid){favs->
                            if(category == "All"){
                                getAllBooks(db, favs){ books->
                                    booksListState.value = books
                                }
                            }else {
                                getAllBooksFromCategory(db, favs, category){ books->
                                    booksListState.value = books
                                }
                            }
                        }
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Главная") },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    }
                )
            },
            bottomBar = {
                BottomMenu(
                    selectedBottomItemState.value ,
                    onFavsClick = {
                        selectedBottomItemState.value = BottomMenuItem.Favs.title
                    getAllFavs(db, navData.uid){favs->
                        getAllFavsBook(db, favs){books->
                            isFavListEmptyState.value = books.isEmpty()
                            booksListState.value = books
                        }
                    }
                },
                    onHomeClick = {
                        selectedBottomItemState.value = BottomMenuItem.Home.title
                    getAllFavs(db, navData.uid){favs->
                        getAllBooks(db, favs){ books->
                            isFavListEmptyState.value = books.isEmpty()
                            booksListState.value = books
                        }
                    }
                },
                    onAccountClick = {
                        selectedBottomItemState.value = BottomMenuItem.Account.title

                    }
            )}
        ){ paddingValues->
            if(isFavListEmptyState.value){
                Box(modifier = Modifier.fillMaxSize(),
                   contentAlignment = Alignment.Center)
                {
                    Text(text = "Empty list",color = Color.LightGray)
                }
            }
            when(selectedBottomItemState.value){
                BottomMenuItem.Account.title->{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Почта: ${navData.email}", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        LogInButton("Выйти"){
                            Firebase.auth.signOut()
                            onLogOut()
                        }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        items(booksListState.value){book->
                            BookListItemUI(
                                isAdminState.value,
                                book,
                                onBookClick={bk->
                                    onBookClick(bk)
                                },
                                onEditClick = {
                                    onBookEditClick(it)
                                },

                                onFavoriteClick = {
                                    booksListState.value = booksListState.value.map {
                                        if (it.key == book.key){
                                            onFavs(
                                                db,
                                                navData.uid,
                                                Favorite(it.key),
                                                !it.isFavorite
                                            )
                                            it.copy(isFavorite = !it.isFavorite)
                                        } else it
                                    }
                                    if(selectedBottomItemState.value == BottomMenuItem.Favs.title){
                                        booksListState.value = booksListState.value.filter { it.isFavorite }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
private fun getAllBooksFromCategory(
    db: FirebaseFirestore,
    idsList: List<String>,
    category: String,
    onBooks: (List<Book>)-> Unit,
) {
    db.collection("books")
        .whereEqualTo("category", category)
        .get()
        .addOnSuccessListener {task->
            val booksList =  task.toObjects(Book::class.java).map {
                if(idsList.contains(it.key)){
                    it.copy(isFavorite = true)
                }else it
            }
            onBooks(booksList)
        }
}
private fun getAllBooks(
    db: FirebaseFirestore,
    idsList: List<String>,
    onBooks: (List<Book>)-> Unit,
) {
    db.collection("books")
        .get()
        .addOnSuccessListener {task->
            val booksList =  task.toObjects(Book::class.java).map {
                if(idsList.contains(it.key)){
                    it.copy(isFavorite = true)
                }else it
            }
            onBooks(booksList)
        }
}
private fun getAllFavsBook(
    db: FirebaseFirestore,
    idsList: List<String>,
    onBooks: (List<Book>)-> Unit
) {
    if(idsList.isNotEmpty()) {
        db.collection("books")
            .whereIn(FieldPath.documentId(), idsList)
            .get()
            .addOnSuccessListener { task ->
                val booksList = task.toObjects(Book::class.java).map {
                    if (idsList.contains(it.key)) {
                        it.copy(isFavorite = true)
                    } else it
                }
                onBooks(booksList)
            }
    }else{
        onBooks(emptyList())
    }
}
private fun getAllFavs(
    db: FirebaseFirestore,
    uid:String,
    onFavs: (List<String>)-> Unit
) {
    db.collection("users")
        .document(uid)
        .collection("favs")
        .get()
        .addOnSuccessListener {task->
            val idsList =  task.toObjects(Book::class.java)
            val keyList = arrayListOf<String>()
            idsList.forEach{
                keyList.add(it.key)
            }
            onFavs(keyList)
        }
}
private fun onFavs(
    db: FirebaseFirestore,
    uid: String,
    favorite: Favorite,
    isFav: Boolean,
){
    if(isFav){
        db.collection("users")
            .document(uid)
            .collection("favs")
            .document(favorite.key)
            .set(favorite)
    }else {
        db.collection("users")
           .document(uid)
           .collection("favs")
           .document(favorite.key)
           .delete()
    }
}


