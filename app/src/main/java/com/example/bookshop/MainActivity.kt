package com.example.bookshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookshop.data.Book
import com.example.bookshop.ui.add_book_screen.AddBookScreen
import com.example.bookshop.ui.add_book_screen.AddScreenObject
import com.example.bookshop.ui.details_screen.data.DetailsNavObject
import com.example.bookshop.ui.details_screen.ui.DetailsScreen
import com.example.bookshop.ui.screens.SignUpScreen
import com.example.bookshop.ui.screens.login.LogInScreen
import com.example.bookshop.ui.screens.login.data.LoginScreenObject
import com.example.bookshop.ui.screens.login.data.MainScreenDataObject
import com.example.bookshop.ui.screens.login.data.SignUpObject
import com.example.bookshop.ui.screens.main_screen.MainScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val auth = Firebase.auth

            NavHost(navController = navController,  startDestination = LoginScreenObject){
                composable<LoginScreenObject> {
                    LogInScreen(navController){navData->
                        navController.navigate(navData)
                    }
                }
                composable<MainScreenDataObject> {
                    val user = auth.currentUser!!
                    val navData = MainScreenDataObject(
                        user.uid,
                        user.email ?: "Нет почты"
                    )

                    MainScreen(
                        navData,
                        onLogOut = {
                            auth.signOut()
                            navController.navigate(LoginScreenObject)
                        },
                        onBookClick = {bk ->
                            navController.navigate(DetailsNavObject(
                                title = bk.title,
                                description = bk.description,
                                price =  bk.price,
                                category = bk.category,
                                year =  bk.year,
                                imageUrl = bk.imageUrl
                            ))
                        },
                        onBookEditClick = { book ->
                        navController.navigate(
                            AddScreenObject(
                                key = book.key,
                                title = book.title,
                                description = book.description,
                                price = book.price,
                                imageUrl = book.imageUrl,
                                year =  book.year,
                                category = book.category,
                            )
                        )},
                    ){
                     navController.navigate(AddScreenObject())
                    }

                }
                composable<AddScreenObject> {navEntry->
                    val navData = navEntry.toRoute<AddScreenObject>()
                    AddBookScreen(navData){
                        navController.popBackStack()
                    }
                }
                composable<DetailsNavObject> {navEntry->
                    val navData = navEntry.toRoute<DetailsNavObject>()
                    DetailsScreen(navData)
                }
                composable<SignUpObject> {
                    SignUpScreen(
                        onBack = {
                            navController.popBackStack()
                        },
                        onNavigateToMainScreen = { navData->
                            navController.navigate(navData)
                        }
                    )
                }
            }
        }
    }


}

//если мы хотим передать какие то данные при навигации то передаем дата класс , если нет то обьект
