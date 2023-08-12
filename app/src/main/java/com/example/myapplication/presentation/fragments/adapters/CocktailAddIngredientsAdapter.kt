package com.example.myapplication.presentation.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.IngredientsAddListItemBinding
import com.example.myapplication.domain.models.IngredientModel

class CocktailAddIngredientsAdapter(
    val ingredients: List<String>
) : RecyclerView.Adapter<CocktailAddIngredientsAdapter.ViewHolder>() {


    class ViewHolder(val binding: IngredientsAddListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IngredientsAddListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ingredientName.text = ingredients[position]
    }
}