package com.example.myapplication.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.fragments.adapters.CocktailsListAdapter
import com.example.myapplication.presentation.viewmodels.CocktailsListViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val SPAN_COUNT = 2

@AndroidEntryPoint
class CocktailsListFragment : Fragment() {

    private val cocktailsListViewModel : CocktailsListViewModel by viewModels()
    //private val clickListener: ClickListener by lazy { ClickListener(fragmentView) }

    private lateinit var fragmentView: View
    private lateinit var clickListener: ClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentView = inflater.inflate(R.layout.fragment_cocktails_list, container, false)
        clickListener = ClickListener(fragmentView)

        //cocktailsListViewModel.test()

        Log.d("ddd", "OnCreateView\nview: {$fragmentView}")

        val recyclerView : RecyclerView = fragmentView.findViewById(R.id.cocktails_recycler_view)
        val adapter = CocktailsListAdapter(
            cocktailsListViewModel.cocktailsList.value ?: emptyList(),
            clickListener
        )
        recyclerView.layoutManager = GridLayoutManager(fragmentView.context, SPAN_COUNT)
        recyclerView.adapter = adapter

        cocktailsListViewModel.loadCocktailsList()

        cocktailsListViewModel.cocktailsList.observe(viewLifecycleOwner) {
            Log.d("ddd", "data changed")
            adapter.setList(cocktailsListViewModel.cocktailsList.value ?: emptyList())
            adapter.notifyDataSetChanged()

            manageLayoutVisibility(fragmentView, cocktailsListViewModel.cocktailsList.value?.isEmpty() ?: true)
        }

        return fragmentView
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

    class ClickListener(private val view: View) : CocktailsListAdapter.OnClickListener {

        override fun onViewClick(id: Long) {
            Log.d("ddd", "smt: $id")
            val bundle = bundleOf("cocktail_id" to id)
            Log.d("ddd", "nav view: {$view}")
            view.findNavController().navigate(R.id.action_cocktailsListFragment_to_cocktailDetailFragment, bundle)
        }
    }

}
