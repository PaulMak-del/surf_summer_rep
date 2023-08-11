package com.example.myapplication.presentation.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.models.IngredientModel

class CocktailsIngredientsAdapter(
    private val ingredients: List<IngredientModel>
) : RecyclerView.Adapter<CocktailsIngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredient: TextView

        init {
            ingredient = view.findViewById(R.id.ingredient_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredient.text = ingredients[position].name
    }

}