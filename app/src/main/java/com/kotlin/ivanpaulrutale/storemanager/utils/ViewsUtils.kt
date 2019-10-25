package com.kotlin.ivanpaulrutale.storemanager.utils


import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.StoreItem

var from_Report_details = false

val listItemObjects: ArrayList<StoreItem> = arrayListOf(
    StoreItem(
        "art_number",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity",
        "store",
        "last_updated"
    ),
    StoreItem(
        "art_number1",
        "color1",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity1",
        "store1",
        "last_updated1"
    ),
    StoreItem(
        "art_number2",
        "color2",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity2",
        "store2",
        "last_updated2"
    ),
    StoreItem(
        "art_number3",
        "color3",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity3",
        "store3",
        "last_updated3"
    ),
    StoreItem(
        "art_number4",
        "color4",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity4",
        "store4",
        "last_updated4"
    ),
    StoreItem(
        "art_number5",
        "color5",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity5",
        "store5",
        "last_updated5"
    )
)

val reportlistItemObjects: ArrayList<ReportItem> = arrayListOf(
    ReportItem(
        "art_number",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "4",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "4",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number2",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "6",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number3",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "8",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number3",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "8",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number4",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "4",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number4",
        "color2",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "4",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number5",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "3",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number5",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "3",
        "store",
        "checked out",
        "Collector Name"
    ),
    ReportItem(
        "art_number2",
        "color2",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "3",
        "store",
        "checked out",
        "Collector Name"
    )
)

fun changeFromFragmentToFragment(activity: FragmentActivity?, fragment: Fragment) {
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(R.id.content_frame, fragment)
        ?.addToBackStack(null)
        ?.commit()
}

fun changeFromActivityToFragment(activity: AppCompatActivity?, fragment: Fragment) {
    activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(R.id.content_frame, fragment)
        ?.addToBackStack(null)
        ?.commit()
}

fun changeFromclassToFragment(activity: AppCompatActivity, fragment: Fragment) {
    activity.supportFragmentManager
        .beginTransaction()
        .replace(R.id.content_frame, fragment)
        .addToBackStack(null)
        .commit()
}

fun noEmptyFields(editTexts: ArrayList<EditText>): Boolean {
    try {
        for (editext in 0..editTexts.lastIndex) {
            val enteredString = editTexts[editext].text.toString()
            if (enteredString.isEmpty()) {
                throw IllegalArgumentException("$editext")
            }
        }
    } catch (e: IllegalArgumentException) {
        val position = e.message?.toInt() ?: 0
        editTexts[position].error = "You left this field empty!"
        return false
    }
    return true
}

fun clearFields(editTexts: ArrayList<EditText>) {
    for (editext in 0..editTexts.lastIndex) {
        editTexts[editext].text.clear()
    }
}


