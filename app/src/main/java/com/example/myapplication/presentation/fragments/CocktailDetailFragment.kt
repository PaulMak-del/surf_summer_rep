package com.example.myapplication.presentation.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCocktailDelailBinding
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.presentation.adapters.CocktailsIngredientsAdapter
import com.example.myapplication.presentation.viewmodels.CocktailDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayInputStream

@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {

    private val cocktailDetailViewModel: CocktailDetailViewModel by viewModels()
    private lateinit var binding: FragmentCocktailDelailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCocktailDelailBinding.inflate(layoutInflater)
        val view = binding.root

        val id : Long = arguments?.getLong("cocktail_id") ?: -1
        cocktailDetailViewModel.loadCocktailWithIngredients(id)

        cocktailDetailViewModel.ingredients.observe(viewLifecycleOwner) {
            setViewParameters(view)
        }
        cocktailDetailViewModel.cocktail.observe(viewLifecycleOwner) {
            setViewParameters(view)
        }

        binding.editButton.setOnClickListener {  }
        binding.deleteButton.setOnClickListener {  }

        return view
    }

    private fun setViewParameters(view: View) {

        val cocktail = cocktailDetailViewModel.cocktail.value ?: CocktailModel(0, "", "", "", byteArrayOf())

        if (!cocktail.image.contentEquals(byteArrayOf())) {
            val stream = ByteArrayInputStream(cocktail.image)
            val image = BitmapFactory.decodeStream(stream)
            binding.cocktailImage.setImageBitmap(image)
        }

        binding.cocktailTi.text = cocktail.name
        if (cocktail.description != "") {
            binding.cocktailDe.visibility = View.VISIBLE
            binding.cocktailDe.text = cocktail.description
        }

        //recycler
        val adapter = CocktailsIngredientsAdapter(cocktailDetailViewModel.ingredients.value ?: emptyList())

        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.ingredientsRecyclerView.adapter = adapter
        (binding.ingredientsRecyclerView.adapter)?.notifyDataSetChanged()

        if (cocktail.recipe != "") {
            binding.recipeTitle.visibility = View.VISIBLE
            binding.recipe.visibility = View.VISIBLE
            binding.recipe.text = cocktail.recipe
        }

        binding.cocktailImage.visibility = View.VISIBLE
        binding.layoutDetail.visibility = View.VISIBLE
    }
}