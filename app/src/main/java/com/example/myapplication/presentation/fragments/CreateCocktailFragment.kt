package com.example.myapplication.presentation.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import com.example.myapplication.databinding.FragmentCreateCocktailBinding
import com.example.myapplication.domain.models.CocktailModel
import com.example.myapplication.presentation.adapters.CocktailAddIngredientsAdapter
import com.example.myapplication.presentation.viewmodels.CreateCocktailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CreateCocktailFragment : Fragment() {

    private val createCocktailViewModel : CreateCocktailViewModel by viewModels()

    private lateinit var binding : FragmentCreateCocktailBinding
    private var imageBitmap: ByteArray = byteArrayOf()
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val im = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap((ImageDecoder.createSource(requireContext().contentResolver, uri)))
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
            val stream = ByteArrayOutputStream()
            im.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            imageBitmap = stream.toByteArray()

            binding.imageId.setImageURI(uri)
            binding.emptyPhoto.visibility = View.GONE
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateCocktailBinding.inflate(layoutInflater)
        val view = binding.root
        val recyclerView = binding.ingredientsAddRecyclerView
        var adapter = CocktailAddIngredientsAdapter(createCocktailViewModel.ingredients.value ?: emptyList(), ClickListenerImpl(createCocktailViewModel))

        recyclerView.adapter = adapter
        createCocktailViewModel.ingredients.observe(viewLifecycleOwner) {
            adapter = CocktailAddIngredientsAdapter(it ?: emptyList(), ClickListenerImpl(createCocktailViewModel))
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
            val recipe = binding.cocktailRecipe.editText?.text.toString()

            val cocktail = CocktailModel(
                0,
                title,
                desc,
                recipe,
                imageBitmap
            )
            createCocktailViewModel.insertCocktailWithIngredients(cocktail)
            view.findNavController().popBackStack()
        }

        binding.iconButton.setOnClickListener {
            Log.d("ddd", "createIng")
            val dialog = AddIngredientDialogFragment(AddIngredientListenerImpl(createCocktailViewModel))
            dialog.show(parentFragmentManager, "AddIngredientDialogFragment")
        }

        binding.imageId.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
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

    class ClickListenerImpl(private val createCocktailViewModel: CreateCocktailViewModel) : CocktailAddIngredientsAdapter.ClickListener {
        override fun onCrossIconClick(ingredientName: String) {
            createCocktailViewModel.removeIngredient(ingredientName)
        }
    }
}