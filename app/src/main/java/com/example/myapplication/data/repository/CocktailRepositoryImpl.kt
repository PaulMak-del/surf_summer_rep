package com.example.myapplication.data.repository

import com.example.myapplication.data.entity.CocktailEntity
import com.example.myapplication.data.entity.CocktailDao
import com.example.myapplication.data.entity.IngredientDao
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao
) : CocktailRepository {

    override fun getCocktail(id: Long): Flow<CocktailModel> =
        cocktailDao.getCocktail(id).map {
            convert(it)
    }

    override fun getCocktailsList(): Flow<List<CocktailModel>> =
        cocktailDao.getCocktailsList().map { cocktailsList ->
            cocktailsList.map {
                convert(it)
            }
        }

    override fun getIngredients(id: Long): Flow<List<IngredientModel>> {
        return ingredientDao.getIngredients(id).map {ingredientList ->
            ingredientList.map {
                IngredientModel(
                    it.id,
                    it.cocktailId,
                    it.name
                )
            }
        }
    }

    override suspend fun insertCocktail(cocktail: CocktailModel) {
        cocktailDao.insertCocktail(CocktailEntity(
            cocktail.id,
            cocktail.name,
            cocktail.description,
            cocktail.recipe,
            cocktail.image))
    }

    private fun convert(cocktailEntity: CocktailEntity) : CocktailModel {
        return CocktailModel(
            cocktailEntity.id,
            cocktailEntity.name,
            cocktailEntity.description,
            cocktailEntity.recipe,
            cocktailEntity.image)
    }
}