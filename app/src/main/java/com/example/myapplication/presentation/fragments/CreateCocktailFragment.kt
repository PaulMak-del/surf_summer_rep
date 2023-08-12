package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        //val view = inflater.inflate(R.layout.fragment_create_cocktail, container, false)
        val binding = FragmentCreateCocktailBinding.inflate(layoutInflater)
        val view = binding.root
        val recyclerView = binding.ingredientsAddRecyclerView
        var adapter = CocktailAddIngredientsAdapter(createCocktailViewModel.ingredients.value ?: emptyList())

        recyclerView.adapter = adapter
        createCocktailViewModel.ingredients.observe(viewLifecycleOwner) {
            adapter = CocktailAddIngredientsAdapter(it ?: emptyList())
            (recyclerView.adapter)?.notifyDataSetChanged()
        }

        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        binding.cancelButton.setOnClickListener {
            Log.d("ddd", "cancelBut")
            view.findNavController().popBackStack()
        }

        binding.saveButton.setOnClickListener {
            Log.d("ddd", "saveBut")
            val title = binding.cocktailTitle.editText?.text.toString()
            val desc = binding.cocktailDescription.editText?.text.toString()
            //val ingredients = createCocktailViewModel.ingredients.value ?: emptyList()
            val recipe = binding.cocktailRecipe.editText?.text.toString()

            val cocktail = CocktailModel(
                0,
                title,
                desc,
                recipe,
                11
            )
            createCocktailViewModel.insertCocktailWithIngredients(cocktail)
            view.findNavController().popBackStack()
        }

        binding.iconButton.setOnClickListener {
            Log.d("ddd", "createIng")
            val dialog = AddIngredientDialogFragment(AddIngredientListenerImpl(createCocktailViewModel))
            dialog.show(parentFragmentManager, "AddIngredientDialogFragment")
        }

        return view
    }

    class AddIngredientListenerImpl(
        private val createCocktailViewModel: CreateCocktailViewModel,
    ) : AddIngredientDialogFragment.AddIngredientListener {
        override fun onPositiveButtonClick(dialog: DialogFragment, ingredientName: String) {
            Log.d("ddd", "onPosButClick()")
            createCocktailViewModel.addIngredient(ingredientName)
            dialog.dialog?.cancel()
        }

        override fun onNegativeButtonClick(dialog: DialogFragment) {
            Log.d("ddd", "onNegButClick()")
            dialog.dialog?.cancel()
        }
    }
}