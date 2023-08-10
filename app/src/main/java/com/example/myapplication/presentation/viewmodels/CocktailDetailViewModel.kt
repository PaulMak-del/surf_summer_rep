package com.example.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.usecases.GetCocktailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    private val getCocktailUseCase: GetCocktailUseCase
) : ViewModel() {

    private val _cocktail = MutableLiveData<CocktailModel>()
    val cocktail : LiveData<CocktailModel> = _cocktail

    fun loadCocktail(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getCocktailUseCase.process(id).collect {
                _cocktail.value = it
            }
        }
    }
}