package com.example.myapplication.di

import com.example.myapplication.data.repository.CocktailRepositoryImpl
import com.example.myapplication.data.repository.IngredientRepositoryImpl
import com.example.myapplication.domain.repository.CocktailRepository
import com.example.myapplication.domain.repository.IngredientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepModule {

    @Binds
    abstract fun bindCocktailRep(rep: CocktailRepositoryImpl) : CocktailRepository

    @Binds
    abstract fun bindIngredientRep(rep: IngredientRepositoryImpl) : IngredientRepository
}