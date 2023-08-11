package com.example.myapplication.domain.repository

import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.models.IngredientModel
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {

    fun getCocktail(id: Long) : Flow<CocktailModel>

    fun getCocktailsList() : Flow<List<CocktailModel>>

    fun getIngredients(id: Long) : Flow<List<IngredientModel>>

    suspend fun insertCocktail(cocktail: CocktailModel)
}