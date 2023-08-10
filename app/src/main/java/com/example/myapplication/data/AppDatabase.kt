package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.entity.CocktailEntity
import com.example.myapplication.data.entity.CocktailDao

@Database(entities = [CocktailEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cocktailDao() : CocktailDao
}