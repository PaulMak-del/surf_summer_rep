package com.example.myapplication.presentation.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.models.CocktailModel
import java.io.ByteArrayInputStream

class CocktailsListAdapter(
    var cocktailsList: List<CocktailModel>,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<CocktailsListAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onViewClick(id : Long)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: ConstraintLayout
        val name: TextView
        val image: ImageFilterView

        init {
            layout = view.findViewById(R.id.cocktail_list_item)
            name = view.findViewById(R.id.recycler_item_text)
            image = view.findViewById(R.id.recycler_item_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cocktails_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = cocktailsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = cocktailsList[position].name

        val stream = ByteArrayInputStream(cocktailsList[position].image)
        holder.image.setImageBitmap(BitmapFactory.decodeStream(stream))

        holder.layout.setOnClickListener { clickListener.onViewClick(cocktailsList[position].id) }
    }
}