package com.example.myapplication.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.domain.usecases.GetCocktailsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val getCocktailsListUseCase: GetCocktailsListUseCase,
) : ViewModel() {

    private val _cocktailsList : MutableLiveData<List<CocktailModel>> by lazy {
        MutableLiveData<List<CocktailModel>>()
    }
    val cocktailsList : LiveData<List<CocktailModel>> = _cocktailsList

    fun loadCocktailsList() {
        viewModelScope.launch(Dispatchers.IO) {
            getCocktailsListUseCase.execute().collect {
                _cocktailsList.postValue(it)
                Log.d("ddd", "cocktails loaded")
            }
        }
    }
}