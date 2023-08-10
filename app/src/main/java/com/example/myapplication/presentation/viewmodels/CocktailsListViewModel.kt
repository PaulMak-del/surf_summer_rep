package com.example.myapplication.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.usecases.GetCocktailUseCase
import com.example.myapplication.domain.usecases.GetCocktailsListUseCase
import com.example.myapplication.domain.usecases.InsertCocktailUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val getCocktailsListUseCase: GetCocktailsListUseCase,
    private val insertCocktailUserCase: InsertCocktailUserCase,
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
        val c1 = CocktailModel(1, "name1", "desc", "rec", 11)
        val c2 = CocktailModel(2, "name2", "desc", "rec", 12)
        val c3 = CocktailModel(3, "name3", "desc", "rec", 13)

        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.process(c1)
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.process(c2)
        }
        viewModelScope.launch(Dispatchers.IO) {
            insertCocktailUserCase.process(c3)
        }
    }
}