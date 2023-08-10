package com.example.myapplication.domain.repository

import com.example.myapplication.domain.models.CocktailModel
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {

    fun getCocktail(id: Long) : Flow<CocktailModel>

    fun getCocktailsList() : Flow<List<CocktailModel>>

    suspend fun insertCocktail(cocktail: CocktailModel)
}