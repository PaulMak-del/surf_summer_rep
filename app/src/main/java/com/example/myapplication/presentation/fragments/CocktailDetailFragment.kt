package com.example.myapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.models.CocktailModel
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

        val id : Long = arguments?.getLong("cocktail_id") ?: -1
        cocktailDetailViewModel.loadCocktailWithIngredients(id)

        cocktailDetailViewModel.ingredients.observe(viewLifecycleOwner) {
            setViewParameters(view)
        }
        cocktailDetailViewModel.cocktail.observe(viewLifecycleOwner) {
            setViewParameters(view)
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun setViewParameters(view: View) {
        val cocktailImageView: ImageView = view.findViewById(R.id.cocktail_image)
        val cocktailName: TextView = view.findViewById(R.id.cocktail_ti)
        val cocktailDescription: TextView = view.findViewById(R.id.cocktail_de)
        val cocktailIngredientsRecyclerView: RecyclerView = view.findViewById(R.id.ingredients_recycler_view)
        val cocktailRecipeTitle: TextView = view.findViewById(R.id.recipe_title)
        val cocktailRecipe: TextView = view.findViewById(R.id.recipe)

        val cocktail = cocktailDetailViewModel.cocktail.value ?: CocktailModel(1, "", "", "", 1)
        cocktailName.text = cocktail.name
        if (cocktail.description != "") cocktailDescription.text = cocktail.description

        //recycler
        val adapter = CocktailsIngredientsAdapter(cocktailDetailViewModel.ingredients.value ?: emptyList())

        cocktailIngredientsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        cocktailIngredientsRecyclerView.adapter = adapter

        if (cocktail.recipe != "") {
            cocktailRecipeTitle.visibility = View.VISIBLE
            cocktailRecipe.text = cocktail.recipe
        }
    }
}