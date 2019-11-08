package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.noEmptyFields
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

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
                val map = hashMapOf(
                    "collector" to collector.text.toString() as Any,
                    "description" to description.text.toString() as Any,
                    "quantity" to quantity.text.toString() as Any
                )
                checkoutItems(map)
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

    private fun checkoutItems(map: HashMap<String, Any>) {
        RetrofitClient.instance.checkoutItem(map).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                when (response.code()) {
                    200 -> {
                        Toast.makeText(activity, "Item checked out", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(
                            activity,
                            "Item could not be checked out",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("FragmentCheckout: ", t.message)
            }
        })
    }
}
