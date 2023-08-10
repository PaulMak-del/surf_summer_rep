package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.presentation.fragments.adapters.CocktailsListAdapter
import com.example.myapplication.presentation.viewmodels.CocktailsListViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val SPAN_COUNT = 2

@AndroidEntryPoint
class CocktailsListFragment : Fragment() {

    private val cocktailsListViewModel : CocktailsListViewModel by viewModels()
    private val clickListener: ClickListener = ClickListener()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cocktails_list, container, false)

        //cocktailsListViewModel.test()

        val recyclerView : RecyclerView = view.findViewById(R.id.cocktails_recycler_view)
        val adapter = CocktailsListAdapter(
            cocktailsListViewModel.cocktailsList.value ?: emptyList(),
            clickListener
        )
        recyclerView.layoutManager = GridLayoutManager(view.context, SPAN_COUNT)
        recyclerView.adapter = adapter

        cocktailsListViewModel.loadCocktailsList()

        cocktailsListViewModel.cocktailsList.observe(viewLifecycleOwner) {
            Log.d("ddd", "data changed")
            adapter.setList(cocktailsListViewModel.cocktailsList.value ?: emptyList())
            adapter.notifyDataSetChanged()

            manageLayoutVisibility(view, cocktailsListViewModel.cocktailsList.value?.isEmpty() ?: true)
        }

        return view
    }

    private fun manageLayoutVisibility(view: View, cocktailsListIsEmpty: Boolean) {
        val summerHolidaysImageView : ImageView = view.findViewById(R.id.summer_holidays)
        val createCockTextView : TextView = view.findViewById(R.id.create_cock)
        val arrowImageView : View = view.findViewById(R.id.arrow_1)
        val recyclerView : RecyclerView = view.findViewById(R.id.cocktails_recycler_view)

        if (cocktailsListIsEmpty) {
            summerHolidaysImageView.visibility = View.VISIBLE
            createCockTextView.visibility = View.VISIBLE
            arrowImageView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            summerHolidaysImageView.visibility = View.GONE
            createCockTextView.visibility = View.GONE
            arrowImageView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

        }
    }

    class ClickListener : CocktailsListAdapter.OnClickListener {
        override fun onViewClick(id: Long) {
            Log.d("ddd", "smt: $id")
        }
    }
}
