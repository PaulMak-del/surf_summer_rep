package com.example.myapplication.data.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CocktailDao {

    @Query("select  * from cocktail where id = :id")
    abstract fun getCocktail(id: Long) : Flow<CocktailEntity>

    @Query("select * from cocktail")
    abstract fun getCocktailsList() : Flow<List<CocktailEntity>>

    @Insert
    abstract fun insertCocktail(cocktail: CocktailEntity) : Long
}