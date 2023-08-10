package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCocktailsListUseCase @Inject constructor(
    private val cocktailRepository: CocktailRepository
) {

    fun execute() = cocktailRepository.getCocktailsList()
}