package com.example.myapplication.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCreateCocktailBinding
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.presentation.fragments.adapters.CocktailAddIngredientsAdapter
import com.example.myapplication.presentation.viewmodels.CreateCocktailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCocktailFragment : Fragment() {

    private val createCocktailViewModel : CreateCocktailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_create_cocktail, container, false)
        val binding = FragmentCreateCocktailBinding.inflate(inflater)
        val recyclerView = binding.ingredientsAddRecyclerView
        var adapter = CocktailAddIngredientsAdapter(createCocktailViewModel.ingredients.value ?: emptyList())
        createCocktailViewModel.ingredients.observe(viewLifecycleOwner) {
            adapter = CocktailAddIngredientsAdapter(it ?: emptyList())
        }

        binding.cancelButton.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.cocktailTitle.editText.toString()
            val desc = binding.cocktailDescription.editText.toString()
            val ingredients = createCocktailViewModel.ingredients.value ?: emptyList()
            val recipe = binding.cocktailRecipe.editText.toString()

            val cocktail = CocktailModel(
                1,
                title,
                desc,
                recipe,
                11
            )
            createCocktailViewModel.insertCocktailWithIngredients(cocktail, ingredients)
        }

        return view
    }
}