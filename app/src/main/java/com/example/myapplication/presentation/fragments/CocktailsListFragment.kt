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
import com.example.myapplication.databinding.FragmentCocktailsListBinding
import com.example.myapplication.presentation.adapters.CocktailsListAdapter
import com.example.myapplication.presentation.viewmodels.CocktailsListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

private const val SPAN_COUNT = 2

@AndroidEntryPoint
class CocktailsListFragment : Fragment() {

    private val cocktailsListViewModel : CocktailsListViewModel by viewModels()
    private lateinit var binding: FragmentCocktailsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("ddd", "onCreateView: list")
        binding = FragmentCocktailsListBinding.inflate(inflater)
        val view = binding.root

        var adapter = CocktailsListAdapter(
            cocktailsListViewModel.cocktailsList.value ?: emptyList(),
            ClickListener(view)
        )
        binding.cocktailsRecyclerView.layoutManager = GridLayoutManager(view.context, SPAN_COUNT)
        binding.cocktailsRecyclerView.adapter = adapter

        // TEST
        binding.myCocktails.setOnClickListener {
            Log.d("ddd", "${adapter.cocktailsList.map{it.id}}")
            Log.d("ddd", "title||adapter:     $adapter")
            Log.d("ddd", "title||rec adapter: ${binding.cocktailsRecyclerView.adapter}")
            (binding.cocktailsRecyclerView.adapter)?.notifyDataSetChanged()
        }

        cocktailsListViewModel.loadCocktailsList()

        cocktailsListViewModel.cocktailsList.observe(viewLifecycleOwner) {
            //Log.d("ddd", "Cocktail List Changed")
            Log.d("ddd", "observer||adapter:     $adapter")
            Log.d("ddd", "observer||rec adapter: ${binding.cocktailsRecyclerView.adapter}")
            adapter = CocktailsListAdapter(it ?: emptyList(), ClickListener(view))
            (binding.cocktailsRecyclerView.adapter)?.notifyDataSetChanged()

            updateUI(view, cocktailsListViewModel.cocktailsList.value?.isEmpty() ?: true)
        }

        binding.floatingActionButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_cocktailsListFragment_to_createCocktailFragment)
        }

        return view
    }

    private fun updateUI(view: View, cocktailsListIsEmpty: Boolean) {
        binding.apply {
            if (cocktailsListIsEmpty) {
                summerHolidays.visibility = View.VISIBLE
                createCock.visibility = View.VISIBLE
                arrow1.visibility = View.VISIBLE
                cocktailsRecyclerView.visibility = View.GONE
            } else {
                summerHolidays.visibility = View.GONE
                createCock.visibility = View.GONE
                arrow1.visibility = View.GONE
                cocktailsRecyclerView.visibility = View.VISIBLE
            }
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
