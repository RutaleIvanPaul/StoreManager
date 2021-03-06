package com.kotlin.ivanpaulrutale.storemanager.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.EditListener
import com.kotlin.ivanpaulrutale.storemanager.utils.SelectionListener
import com.kotlin.ivanpaulrutale.storemanager.utils.Utils
import kotlinx.android.synthetic.main.edit_check_out_item.*

/**
 * Created by Derick W on 22,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class CheckOutEditBottomSheet : BottomSheetDialogFragment() {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var mCallback : EditListener
    var reportItem : ReportItem? = null
    var selectedStore : Stores? = null
    lateinit var itemStore : Stores

    companion object {
        fun newInstance(mListener : EditListener, reportItem: ReportItem) : CheckOutEditBottomSheet {
            val obj = CheckOutEditBottomSheet()
            obj.mCallback = mListener
            obj.reportItem = reportItem
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
        return inflater.inflate(R.layout.edit_check_out_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportItem?.let {
            checkout_art_number.text = Editable.Factory.getInstance().newEditable(it.artNumber)
            checkout_color.text = Editable.Factory.getInstance().newEditable(it.color)
            checkout_description.text = Editable.Factory.getInstance().newEditable(it.description)
            checkout_collector.text = Editable.Factory.getInstance().newEditable(it.collector)
            checkout_store.text = Editable.Factory.getInstance().newEditable(it.store)
            checkout_quantity.text = Editable.Factory.getInstance().newEditable(it.checkOutQuantity.toString())
            itemStore = Stores(it.storeId, it.store, false)
        }

        checkout_button_edit.setOnClickListener {
            if (Utils.validated(checkout_quantity, checkout_collector, checkout_store)) {
                dismiss()
                val map = hashMapOf(
                    "artNumber" to checkout_art_number.text.toString() as Any,
                    "color" to checkout_color.text.toString() as Any,
                    "description" to checkout_description.text.toString() as Any,
                    "quantity" to checkout_quantity.text.toString() as Any,
                    "collector" to checkout_collector.text.toString() as Any,
                    "storeId" to (selectedStore?.id ?: itemStore.id).toString() as Any,
                    "store" to (selectedStore?.store ?: itemStore.store) as Any
                )
                reportItem?.let { mCallback.editCheckIn(it.id, map) }
            }
        }


        checkout_store.setOnClickListener {
            StoreBottomSheet.newInstance(RetrofitClient, object : SelectionListener {
                override fun getStore(store: Stores) {
                    selectedStore = store
                    checkout_store.text = Editable.Factory.getInstance().newEditable(store.store)
                }

            }).show(childFragmentManager, "stores")
        }
    }
}
