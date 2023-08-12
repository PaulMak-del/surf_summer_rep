package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.repository.CocktailRepository
import javax.inject.Inject

class InsertCocktailUserCase @Inject constructor(
    private val cocktailRep: CocktailRepository
) {

    suspend fun execute(cocktail: CocktailModel) {
        cocktailRep.insertCocktail(cocktail)
    }
}