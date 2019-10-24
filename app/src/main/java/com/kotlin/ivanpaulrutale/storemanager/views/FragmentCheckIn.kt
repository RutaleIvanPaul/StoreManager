package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.kotlin.ivanpaulrutale.storemanager.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentCheckIn : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_check_in, container, false)

        view.findViewById<Button>(R.id.check_in_button).setOnClickListener {
            val art_number = view.findViewById<EditText>(R.id.check_in_art_number)
            val color = view.findViewById<EditText>(R.id.check_in_color)
            val description = view.findViewById<EditText>(R.id.check_in_description)
            val quantity = view.findViewById<EditText>(R.id.check_in_quantity)
            val store = view.findViewById<EditText>(R.id.check_in_store)

            val editTexts = arrayListOf<EditText>(art_number,color,description,quantity,store)

            if (noEmptyFields(editTexts)){
                Toast.makeText(context,art_number.text, Toast.LENGTH_LONG).show()
                clearFields(editTexts)
            }
        }
        return view
    }
}
