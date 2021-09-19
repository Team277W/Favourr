package com.example.favourr.models

import java.io.Serializable


data class FavourrModel @JvmOverloads constructor(
    val _id: String? = null,
    val user: String? = null,
    val title: String,
    val body: String,
    val contact: String,
    val city: String,
    val cash: Double,
    val status: Int? = null
) : Serializable
