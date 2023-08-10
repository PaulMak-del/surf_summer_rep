package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.repository.CocktailRepository
import javax.inject.Inject

class GetCocktailUseCase @Inject constructor(
    private val cocktailRep: CocktailRepository
){

    fun process(id: Long) = cocktailRep.getCocktail(id)
}