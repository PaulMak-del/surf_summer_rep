package com.example.myapplication.data.repository

import com.example.myapplication.data.entity.IngredientDao
import com.example.myapplication.data.entity.IngredientEntity
import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao
) : IngredientRepository {

    override fun insertIngredients(ingredients: List<IngredientModel>) = ingredientDao.insertIngredients(
        ingredients.map { ingredient ->
            IngredientEntity(
                id = ingredient.id,
                cocktailId = ingredient.cocktailId,
                name = ingredient.name
            )
        }
    )

    override fun getIngredients(id: Long): Flow<List<IngredientModel>> =
        ingredientDao.getIngredients(id).map { ingredientList ->
            ingredientList.map {
                IngredientModel(
                    it.id,
                    it.cocktailId,
                    it.name
                )
            }
        }
}