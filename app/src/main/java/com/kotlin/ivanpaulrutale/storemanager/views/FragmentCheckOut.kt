package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.GenericResponse
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.Utils
import com.kotlin.ivanpaulrutale.storemanager.utils.remove
import com.kotlin.ivanpaulrutale.storemanager.utils.show
import kotlinx.android.synthetic.main.fragment_check_out.*
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
    private var itemID: Int = 0
    private var quantityValue: Int = 0

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkout_button.setOnClickListener {
            if (Utils.validated(checkout_art_number, checkout_color, checkout_description, checkout_quantity, checkout_store, checkout_collector) && itemID != 0) {
                if (checkout_quantity.text.toString().toInt() > quantityValue) {
                    Toast.makeText(activity?.applicationContext, "You can not checkout more than $quantityValue items.", Toast.LENGTH_LONG).show()
                } else {
                    val map = hashMapOf(
                        "collector" to checkout_collector.text.toString() as Any,
                        "description" to checkout_description.text.toString() as Any,
                        "quantity" to checkout_quantity.text.toString() as Any,
                        "store" to checkout_store.text.toString() as Any
                    )
                    showProgress()
                    checkoutItems(it, itemID, map)
                }
            }
        }

    }

    private fun showProgress() {
        checking_out.show()

        storeLayout.remove()
        artNumberLayout.remove()
        colorLayout.remove()
        descriptionLayout.remove()
        quantityLayout.remove()
        checkout_button.remove()
    }

    private fun hideProgress() {
        checking_out.remove()

        artNumberLayout.show()
        colorLayout.show()
        descriptionLayout.show()
        quantityLayout.show()
        checkout_button.show()
    }

    private fun getDataFromBundle() {
        if (arguments != null) {
            art_number = arguments!!["art_number"] as String
            color = arguments!!["color"] as String
            description = arguments!!["description"] as String
            store = arguments!!["store"] as String
            itemID = arguments!!["id"] as Int
            quantityValue = arguments!!["quantity"] as Int
        }
    }

    private fun checkoutItems(view : View, id : Int, map: HashMap<String, Any>) {
        RetrofitClient.instance.checkoutItem(id, map).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: retrofit2.Response<GenericResponse>) {
                when (response.code()) {
                    200 -> {
                        hideProgress()
                        Toast.makeText(activity, "Item checked out", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view)
                            .navigate(R.id.fragmentSearch)
                    }
                    else -> {
                        hideProgress()
                        Toast.makeText(
                            activity,
                            "Item could not be checked out",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("FragmentCheckout: ", t.message)
            }
        })
    }
}
