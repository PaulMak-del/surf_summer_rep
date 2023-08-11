package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.repository.IngredientRepository
import javax.inject.Inject

class InsertIngredientsUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {

    fun execute(ingredients: List<IngredientModel>) = ingredientRepository.insertIngredients(ingredients)
}