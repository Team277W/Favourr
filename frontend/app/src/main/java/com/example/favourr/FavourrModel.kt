package com.example.favourr

import java.io.Serializable

data class FavourrModel(
    val _id: String,
    val title: String,
    val body: String,
    val contact: String,
    val city: String,
    val cash: Int,
    val status: Int
) : Serializable
