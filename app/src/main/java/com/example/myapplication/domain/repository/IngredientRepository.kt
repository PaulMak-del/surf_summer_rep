package com.example.myapplication.domain.repository

import com.example.myapplication.domain.models.IngredientModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    fun insertIngredients(ingredients: List<IngredientModel>)

    fun getIngredients(id: Long) : Flow<List<IngredientModel>>
}