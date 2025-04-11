package com.example.bookshop.ui.details_screen.data

import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavObject(
    val title : String ="",
    val description: String ="",
    val price : String = "",
    val category: String ="",
    val year: String = "",
    val imageUrl: String =""
)