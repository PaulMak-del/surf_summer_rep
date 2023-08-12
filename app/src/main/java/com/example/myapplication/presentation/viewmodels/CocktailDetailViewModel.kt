package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.models.IngredientModel
import com.example.myapplication.domain.usecases.GetCocktailUseCase
import com.example.myapplication.domain.usecases.GetIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    private val getCocktailUseCase: GetCocktailUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
) : ViewModel() {

    private val _cocktail = MutableLiveData<CocktailModel>(CocktailModel(1, "", "", "", 1))
    val cocktail : LiveData<CocktailModel> = _cocktail

    private val _ingredients = MutableLiveData<List<IngredientModel>>()
    val ingredients : LiveData<List<IngredientModel>> = _ingredients



    fun loadCocktailWithIngredients(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getCocktailUseCase.process(id).collect {
                _cocktail.postValue(it)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getIngredientsUseCase.execute(id).collect {
                _ingredients.postValue(it)
            }
        }
    }

}