package com.example.myapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.IngredientsAddListItemBinding

class CocktailAddIngredientsAdapter(
    val ingredients: List<String>,
    private val listener: ClickListener
) : RecyclerView.Adapter<CocktailAddIngredientsAdapter.ViewHolder>() {

    interface ClickListener {
        fun onCrossIconClick(toString: String)
    }

    class ViewHolder(val binding: IngredientsAddListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IngredientsAddListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ingredientName.text = ingredients[position]

        holder.binding.crossImage.setOnClickListener { listener.onCrossIconClick(holder.binding.ingredientName.text.toString()) }
    }
}