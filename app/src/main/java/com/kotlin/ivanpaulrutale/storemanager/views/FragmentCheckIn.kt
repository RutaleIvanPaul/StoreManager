package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.GenericResponse
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.SelectionListener
import com.kotlin.ivanpaulrutale.storemanager.utils.clearFields
import com.kotlin.ivanpaulrutale.storemanager.utils.noEmptyFields
import kotlinx.android.synthetic.main.fragment_check_in.*
import retrofit2.Call
import retrofit2.Callback

/**
 * A simple [Fragment] subclass.
 */
class FragmentCheckIn : Fragment() {

    var selectedStore : Stores? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_check_in, container, false)

        view.findViewById<Button>(R.id.check_in_button).setOnClickListener {
            val editTexts = arrayListOf<EditText>(check_in_art_number, check_in_color, check_in_description, check_in_quantity, check_in_store)

            if (noEmptyFields(editTexts)) {
                val map = hashMapOf(
                    "artNumber" to check_in_art_number.text.toString() as Any,
                    "color" to check_in_color.text.toString() as Any,
                    "description" to check_in_description.text.toString() as Any,
                    "quantity" to check_in_quantity.text.toString() as Any,
                    "storeId" to selectedStore?.id.toString() as Any
                )
                checkInItems(map)
                clearFields(editTexts)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check_in_store.setOnClickListener {
            StoreBottomSheet.newInstance(RetrofitClient, object : SelectionListener {
                override fun getStore(store: Stores) {
                    selectedStore = store
                    check_in_store.text = Editable.Factory.getInstance().newEditable(store.store)
                }

            }).show(childFragmentManager, "stores")
        }
    }

    private fun checkInItems(map: HashMap<String, Any>) {
        RetrofitClient.instance.checkInItem(map).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: retrofit2.Response<GenericResponse>) {
                when (response.code()) {
                    200 -> { showMessage("Item checked in") }
                    201 -> { showMessage("Item checked in") }
                    else -> { showMessage("Item could not be checked in") }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("FragmentCheckIn: ", t.message)
            }
        })
    }

    private fun showMessage(message : String) = Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
}
