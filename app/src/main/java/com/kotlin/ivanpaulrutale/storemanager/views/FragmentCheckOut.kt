package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.utils.noEmptyFields

/**
 * A simple [Fragment] subclass.
 */
class FragmentCheckOut : Fragment() {
    private lateinit var art_number: String
    private lateinit var color: String
    private lateinit var description: String
    private lateinit var store: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check_out, container, false)

        getDataFromBundle()

        view.findViewById<TextInputEditText>(R.id.checkout_art_number).setText(art_number)
        view.findViewById<TextInputEditText>(R.id.checkout_color).setText(color)
        view.findViewById<TextInputEditText>(R.id.checkout_description).setText(description)
        view.findViewById<TextInputEditText>(R.id.checkout_store).setText(store)

        view.findViewById<Button>(R.id.checkout_button).setOnClickListener {
            val art_number = view.findViewById<EditText>(R.id.checkout_art_number)
            val color = view.findViewById<EditText>(R.id.checkout_color)
            val description = view.findViewById<EditText>(R.id.checkout_description)
            val quantity = view.findViewById<EditText>(R.id.checkout_quantity)
            val store = view.findViewById<EditText>(R.id.checkout_store)
            val collector = view.findViewById<EditText>(R.id.checkout_collector)

            val editTexts =
                arrayListOf<EditText>(art_number, color, description, quantity, store, collector)

            if (noEmptyFields(editTexts)) {
                Toast.makeText(context, art_number.text, Toast.LENGTH_LONG).show()
//                changeFromFragmentToFragment(activity, Search())
            }

        }
        return view
    }

    private fun getDataFromBundle() {
        if (arguments != null) {
            art_number = arguments!!["art_number"] as String
            color = arguments!!["color"] as String
            description = arguments!!["description"] as String
            store = arguments!!["store"] as String
        }
    }
}
