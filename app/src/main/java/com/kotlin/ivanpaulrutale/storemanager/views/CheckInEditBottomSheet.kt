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
import com.kotlin.ivanpaulrutale.storemanager.models.Store
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.EditListener
import com.kotlin.ivanpaulrutale.storemanager.utils.SelectionListener
import com.kotlin.ivanpaulrutale.storemanager.utils.Utils
import kotlinx.android.synthetic.main.edit_check_in_item.*

/**
 * Created by Derick W on 22,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class CheckInEditBottomSheet : BottomSheetDialogFragment() {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var mCallback : EditListener
    var selectedStore : Stores? = null
    var checkInItem : Store? = null

    companion object {
        fun newInstance(checkInItem : Store, mListener : EditListener) : CheckInEditBottomSheet {
            val obj = CheckInEditBottomSheet()
            obj.checkInItem = checkInItem
            obj.mCallback = mListener
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
        return inflater.inflate(R.layout.edit_check_in_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInItem?.let {
            check_in_art_number.text = Editable.Factory.getInstance().newEditable(it.artNumber)
            check_in_color.text = Editable.Factory.getInstance().newEditable(it.color)
            check_in_description.text = Editable.Factory.getInstance().newEditable(it.description)
            check_in_store.text = Editable.Factory.getInstance().newEditable(it.stores?.store)
            check_in_quantity.text = Editable.Factory.getInstance().newEditable(it.quantity.toString())
        }

        check_in_store.setOnClickListener {
            StoreBottomSheet.newInstance(RetrofitClient, object : SelectionListener {
                override fun getStore(store: Stores) {
                    selectedStore = store
                    check_in_store.text = Editable.Factory.getInstance().newEditable(store.store)
                }

            }).show(childFragmentManager, "stores")
        }

        check_in_button_edit.setOnClickListener {
            if (Utils.validated(check_in_art_number, check_in_color, check_in_description, check_in_store, check_in_quantity)) {
                dismiss()
                val map = hashMapOf(
                    "artNumber" to check_in_art_number.text.toString() as Any,
                    "color" to check_in_color.text.toString() as Any,
                    "description" to check_in_description.text.toString() as Any,
                    "quantity" to check_in_quantity.text.toString() as Any
                )
                checkInItem?.let { item -> mCallback.editCheckIn(item.id, map) }
            }
        }

    }



}
