package com.example.myapplication.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout
import java.lang.IllegalStateException

class AddIngredientDialogFragment(private val listener: AddIngredientListener) : DialogFragment() {

    interface AddIngredientListener {
        fun onPositiveButtonClick(dialog: DialogFragment, ingredientName: String)
        fun onNegativeButtonClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.add_ingredient_dialog_alert, null)

            val saveButton: Button = view.findViewById(R.id.save_button)
            val cancelButton: Button = view.findViewById(R.id.cancel_button)
            val ingredientName: TextInputLayout = view.findViewById(R.id.ingredient)

            builder.setView(view)
            saveButton.setOnClickListener { listener.onPositiveButtonClick(this, ingredientName.editText?.text.toString()) }
            cancelButton.setOnClickListener { listener.onNegativeButtonClick(this) }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}