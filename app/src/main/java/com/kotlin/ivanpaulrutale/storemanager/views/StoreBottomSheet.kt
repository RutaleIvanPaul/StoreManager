package com.kotlin.ivanpaulrutale.storemanager.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.adapter.StoresAdapter
import com.kotlin.ivanpaulrutale.storemanager.models.StoreResponse
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import kotlinx.android.synthetic.main.stores_bottom_sheet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Derick W on 21,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class StoreBottomSheet : BottomSheetDialogFragment() {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>
    private var itemList: MutableList<Stores> = mutableListOf()
    private lateinit var adapter: StoresAdapter
    var serviceInstance = RetrofitClient
    lateinit var mCallback : FragmentCheckIn.SelectionListener

    companion object {
        fun newInstance(services : RetrofitClient, selectionListener: FragmentCheckIn.SelectionListener) : StoreBottomSheet {
            val obj = StoreBottomSheet()
            obj.serviceInstance = services
            obj.mCallback = selectionListener
            return obj
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val sheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(sheet!!)
            behavior.isHideable = true
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog)
        }
        return dialog
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setWhiteNavigationBar(dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            val metrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(metrics)

            val dimDrawable = GradientDrawable()

            val navigationBarDrawable = GradientDrawable()
            navigationBarDrawable.shape = GradientDrawable.RECTANGLE
            navigationBarDrawable.setColor(Color.WHITE)

            val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)

            val windowBackground = LayerDrawable(layers)
            windowBackground.setLayerInsetTop(1, metrics.heightPixels)

            window.setBackgroundDrawable(windowBackground)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.stores_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StoresAdapter(activity!!.applicationContext, itemList)

        val linearLayoutManager = LinearLayoutManager(activity)
        stores_recycler_view.layoutManager = linearLayoutManager
        stores_recycler_view.adapter = adapter

        fetchStores()

        next.setOnClickListener {
            dismiss()
            adapter.getSelectedStore()?.let { it1 -> mCallback.getStore(it1) }
        }
    }

    private fun fetchStores() {
        search_progressBar.visibility = View.VISIBLE
        serviceInstance.instance.getStores().enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.d("stores", response.toString())
                when (response.code()) {
                    200 -> {
                        search_progressBar.visibility = View.GONE
                        next.visibility = View.VISIBLE
                        itemList.addAll(response.body()!!.storeItems as MutableList<Stores>)
                        adapter.notifyDataSetChanged()

                    }
                    400 -> {
                        Toast.makeText(activity, "Stores not found", Toast.LENGTH_SHORT).show()
                    }
                    404 -> {
                        Toast.makeText(activity, "Stores not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                Log.e("Stores Loading: ", t.message)
            }
        })
    }

}
