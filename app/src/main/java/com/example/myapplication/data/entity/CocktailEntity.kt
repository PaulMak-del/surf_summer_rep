package com.example.myapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktail")
data class CocktailEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val recipe: String,
    val image: Int
)

@Entity(tableName = "ingredient")
data class IngredientEntity(
    @PrimaryKey val id: Long,
    val cocktailId: Long,
    val name: String
)
