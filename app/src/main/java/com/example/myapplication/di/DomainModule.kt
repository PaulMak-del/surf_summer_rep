package com.example.myapplication.di

import com.example.myapplication.domain.repository.CocktailRepository
import com.example.myapplication.domain.usecases.GetCocktailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideGetCocktailUseCase(rep: CocktailRepository) = GetCocktailUseCase(rep)
}