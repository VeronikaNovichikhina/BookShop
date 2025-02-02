package com.example.bookshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookshop.ui.add_book_screen.AddBookScreen
import com.example.bookshop.ui.add_book_screen.AddScreenObject
import com.example.bookshop.ui.screens.SignUpScreen
import com.example.bookshop.ui.screens.login.LogInScreen
import com.example.bookshop.ui.screens.login.data.LoginScreenObject
import com.example.bookshop.ui.screens.login.data.MainScreenDataObject
import com.example.bookshop.ui.screens.login.data.SignUpObject
import com.example.bookshop.ui.screens.main_screen.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController,  startDestination = LoginScreenObject){
                composable<LoginScreenObject> {
                    LogInScreen(navController){navData->
                        navController.navigate(navData)
                    }
                }
                composable<MainScreenDataObject> {navEntry->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen(navData)
                }
                composable<AddScreenObject> {navEntry->
                    AddBookScreen()
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
