package com.example.bookshop.ui.screens.main_screen.bottom_menu

import com.example.bookshop.R


sealed class BottomMenuItem(
    val route: String,
    val iconId: Int,
    val title: String
) {
    object Home : BottomMenuItem(
        "",
        R.drawable.home,
        "Home"
    )
    object Favs : BottomMenuItem(
        "",
        R.drawable.fav,
        "Favs"
    )
    object Settings : BottomMenuItem(
        "",
        R.drawable.settings,
        "Setting"
    )
}