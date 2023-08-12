package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.usecases.GetCocktailsListUseCase
import com.example.myapplication.domain.usecases.InsertCocktailUserCase
import com.example.myapplication.domain.usecases.InsertIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val getCocktailsListUseCase: GetCocktailsListUseCase,
    private val insertCocktailUserCase: InsertCocktailUserCase,
    private val insertIngredientUseCase: InsertIngredientsUseCase,
) : ViewModel() {

    private val _cocktailsList = MutableLiveData<List<CocktailModel>>()
    val cocktailsList : LiveData<List<CocktailModel>> = _cocktailsList

    fun loadCocktailsList() {
        viewModelScope.launch(Dispatchers.IO) {
            getCocktailsListUseCase.execute().collect {
                _cocktailsList.postValue(it)
            }
        }
    }

    fun test() {
        val c1 = CocktailModel(0, "name1", "desc", "rec", 11)
        val c2 = CocktailModel(0, "name2", "desc", "rec", 12)
        val c3 = CocktailModel(0, "name3", "desc", "rec", 13)
        val ing1 = IngredientModel(0, 1, "ing1_")
        val ing2 = IngredientModel(0, 1, "ing2_")
        val ing3 = IngredientModel(0, 2, "ing1__")
        val ing4 = IngredientModel(0, 2, "ing2__")
        val ing5 = IngredientModel(0, 2, "ing3__")
        val ing6 = IngredientModel(0, 3, "ing1___")
        val ing7 = IngredientModel(0, 3, "ing2___")

        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.execute(c1)
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.execute(c2)
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.execute(c3)
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertIngredientUseCase.execute(listOf(ing1, ing2))
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertIngredientUseCase.execute(listOf(ing3, ing4, ing5))
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertIngredientUseCase.execute(listOf(ing6, ing7))
        }
    }
}