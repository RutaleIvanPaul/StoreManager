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
import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.utils.Utils
import com.kotlin.ivanpaulrutale.storemanager.utils.remove
import com.kotlin.ivanpaulrutale.storemanager.utils.show
import kotlinx.android.synthetic.main.manage_stores.*

/**
 * Created by Derick W on 25,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ManageStoresBottomSheet : BottomSheetDialogFragment() {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var status : String
    private lateinit var mCallback : FragmentStores.StoresListener
    var store : Stores? = null

    companion object {
        fun newInstance(stores: Stores?, status : String, storesListener: FragmentStores.StoresListener) : ManageStoresBottomSheet {
            val obj = ManageStoresBottomSheet()
            obj.status = status
            obj.mCallback = storesListener
            obj.store = stores
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
        return inflater.inflate(R.layout.manage_stores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (status.contentEquals(Constants.ADD))
            confirm.text = activity!!.applicationContext.getString(R.string.add_store)

        if (status.contentEquals(Constants.EDIT)) {
            confirm.text = activity!!.applicationContext.getString(R.string.edit_store)
            store_layout.hint = activity!!.applicationContext.getString(R.string.edit_store_name)
            storeName.text = Editable.Factory.getInstance().newEditable(store?.store)
        }

        if (status.contentEquals(Constants.DELETE)) {
            confirm.text = activity!!.applicationContext.getString(R.string.delete_store)
            store_layout.hint = activity!!.applicationContext.getString(R.string.store)
            storeName.text = Editable.Factory.getInstance().newEditable(store?.store)

            val storeValue = store!!.store
            activity?.applicationContext?.let {
                confirmation_message.text = it.getString(R.string.deletion_confirm, storeValue)
            }
            storeName.remove()
            confirmation_message.show()
        }

        confirm.setOnClickListener {
            if (Utils.validated(storeName)) {
                if (status.contentEquals(Constants.ADD)) {
                    dismiss()
                    mCallback.addStore(storeName.text.toString())
                }

                if (status.contentEquals(Constants.EDIT)) {
                    store?.let { it1 ->
                        if (it1.store.equals(storeName.text.toString(), true)) {
                            dismiss()
                        } else {
                            dismiss()
                            mCallback.editStore(it1, storeName.text.toString())
                        }
                    }
                }

                if (status.contentEquals(Constants.DELETE)) {
                    store?.let { it1 ->
                        dismiss()
                        mCallback.deleteStore(it1)
                    }
                }
            }
        }
    }

}
