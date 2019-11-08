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
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.clearFields
import com.kotlin.ivanpaulrutale.storemanager.utils.noEmptyFields
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

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

            val editTexts = arrayListOf<EditText>(art_number, color, description, quantity, store)

            if (noEmptyFields(editTexts)) {
                val map = hashMapOf(
                    "artNumber" to art_number.text.toString() as Any,
                    "color" to color.text.toString() as Any,
                    "description" to description.text.toString() as Any,
                    "quantity" to quantity.text.toString() as Any,
                    "store" to store.text.toString() as Any
                )
                checkinItems(map)
                clearFields(editTexts)
            }
        }
        return view
    }

    private fun checkinItems(map: HashMap<String, Any>) {
        RetrofitClient.instance.checkinItem(map).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                when (response.code()) {
                    200 -> {
                        Toast.makeText(activity, "Item checked in", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(
                            activity,
                            "Item could not be checked in",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("FragmentCheckin: ", t.message)
            }
        })
    }
}
