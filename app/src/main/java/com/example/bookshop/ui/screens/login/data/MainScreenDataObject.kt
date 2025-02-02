package com.example.bookshop.ui.screens.login.data

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenDataObject(
    val uid: String ="",
    val email: String="",
)
