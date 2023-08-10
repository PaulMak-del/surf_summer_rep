package com.example.myapplication.domain.models

data class CocktailModel(
    val id: Long,
    val name: String,
    val description: String,
    val recipe: String,
    val image: Int,
)

data class IngredientModel(
    val id: Long,
    val cocktailId: Long,
    val name: String
)
