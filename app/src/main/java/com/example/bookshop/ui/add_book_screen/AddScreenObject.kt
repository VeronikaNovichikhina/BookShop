package com.example.bookshop.ui.add_book_screen

import kotlinx.serialization.Serializable

@Serializable
data class AddScreenObject(
    val key : String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val year: String = "",
    val imageUrl: String = "",
)