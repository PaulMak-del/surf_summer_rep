package com.example.myapplication.data.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query("select * from ingredient where cocktailId = :cocktailId")
    fun getIngredients(cocktailId: Long) : Flow<List<IngredientEntity>>

    @Insert
    fun insertIngredients(ingredients: List<IngredientEntity>)
}