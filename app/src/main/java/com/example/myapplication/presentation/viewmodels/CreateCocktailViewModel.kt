package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.usecases.GetIngredientsUseCase
import com.example.myapplication.domain.usecases.InsertCocktailUserCase
import com.example.myapplication.domain.usecases.InsertIngredientsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateCocktailViewModel @Inject constructor(
    private val insertCocktailUserCase: InsertCocktailUserCase,
    private val insertIngredientsUseCase: InsertIngredientsUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
) : ViewModel() {

    private val _ingredients = MutableLiveData<List<IngredientModel>>()
    val ingredients : LiveData<List<IngredientModel>> = _ingredients

    fun loadIngredients(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getIngredientsUseCase.execute(id).collect {
                _ingredients.postValue(it)
            }
        }
    }

    fun insertIngredient(ings: List<IngredientModel>) {
        viewModelScope.launch(Dispatchers.IO){
            insertIngredientsUseCase.execute(ings)
        }
    }

    fun insertCocktail(cocktail: CocktailModel) {
        viewModelScope.launch(Dispatchers.IO){
            insertCocktailUserCase.execute(cocktail)
        }
    }

    fun insertCocktailWithIngredients(cocktail: CocktailModel, ings: List<IngredientModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.execute(cocktail)
            insertIngredientsUseCase.execute(ings)
        }
    }
}