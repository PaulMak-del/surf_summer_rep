package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.entity.CocktailEntity
import com.example.myapplication.data.entity.CocktailDao
import com.example.myapplication.data.entity.IngredientDao
import com.example.myapplication.data.entity.IngredientEntity

@Database(entities = [CocktailEntity::class, IngredientEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cocktailDao() : CocktailDao

    abstract fun ingredientDao() : IngredientDao
}