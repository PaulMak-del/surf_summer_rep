package com.example.myapplication.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.usecases.GetIngredientsUseCase
import com.example.myapplication.domain.usecases.InsertCocktailUserCase
import com.example.myapplication.domain.usecases.InsertIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCocktailViewModel @Inject constructor(
    private val insertCocktailUserCase: InsertCocktailUserCase,
    private val insertIngredientsUseCase: InsertIngredientsUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
) : ViewModel() {

    private val mutIngredientsList : MutableList<String> = mutableListOf()
    private val _ingredients = MutableLiveData<List<String>>(mutIngredientsList)
    val ingredients : LiveData<List<String>> = _ingredients

    fun insertCocktailWithIngredients(cocktail: CocktailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val cocktailId = insertCocktailUserCase.execute(cocktail)
            insertIngredientsUseCase.execute(
                _ingredients.value?.map {
                    IngredientModel(
                        id = 0,
                        cocktailId = cocktailId,
                        name = it
                    )
                } ?: emptyList()
            )
        }
    }

    fun addIngredient(name: String) {
        Log.d("ddd", "add ing: {$name}")
        mutIngredientsList.add(name)
        _ingredients.value = mutIngredientsList
    }
}