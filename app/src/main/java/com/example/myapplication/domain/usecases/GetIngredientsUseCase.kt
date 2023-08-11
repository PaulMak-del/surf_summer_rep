package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIngredientsUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository
) {

    fun execute(id: Long): Flow<List<IngredientModel>> {
        return cocktailRepository.getIngredients(id)
    }
}