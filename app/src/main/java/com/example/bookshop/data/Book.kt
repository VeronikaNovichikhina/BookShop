package com.example.bookshop.data

data class Book (
    val key : String = "",
    val imageUrl: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val year: String = "",
    val isFavorite: Boolean = false,
)