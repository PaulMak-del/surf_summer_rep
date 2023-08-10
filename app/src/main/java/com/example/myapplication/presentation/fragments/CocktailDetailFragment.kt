package com.example.myapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.fragments.adapters.CocktailsIngredientsAdapter
import com.example.myapplication.presentation.viewmodels.CocktailDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {

    private val cocktailDetailViewModel: CocktailDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cocktail_delail, container, false)

        val id : Long = 1 //TODO: Get id from CocktailsListFragment
        cocktailDetailViewModel.loadCocktail(id)

        val recyclerView : RecyclerView = view.findViewById(R.id.ingredients_recycler_view)
        //TODO: Fix ingredients (Add Ing rep and usecase)
        val adapter = CocktailsIngredientsAdapter(listOf("ing1", "ing2", "ing3"))
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter

        //TODO: Fix fragment behavior
        if (cocktailDetailViewModel.cocktail.value?.description == null) {
            view.findViewById<TextView>(R.id.cocktail_de).visibility = View.GONE
        }
        if (cocktailDetailViewModel.cocktail.value?.recipe == null) {
            view.findViewById<TextView>(R.id.recipe).visibility = View.GONE
        }

        // Inflate the layout for this fragment
        return view
    }
}