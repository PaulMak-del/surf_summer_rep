package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()

    @Provides
    fun providesCocktailDao(db: AppDatabase) = db.cocktailDao()

    @Provides
    fun providesIngredientDao(db: AppDatabase) = db.ingredientDao()
}