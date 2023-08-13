package com.example.myapplication.domain.models

data class CocktailModel(
    val id: Long,
    val name: String,
    val description: String,
    val recipe: String,
    val image: ByteArray,
)

