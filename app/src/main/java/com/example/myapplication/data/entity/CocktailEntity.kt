package com.example.myapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktail")
data class CocktailEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name: String,
    val description: String,
    val recipe: String,
    val image: Int
)

