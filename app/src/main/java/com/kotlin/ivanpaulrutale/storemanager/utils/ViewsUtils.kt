package com.kotlin.ivanpaulrutale.storemanager.utils


import android.widget.EditText
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.Store

var from_Report_details = false

val listItemObjects: ArrayList<Store> = arrayListOf(
    Store(
        1,
        1,
        "art_number",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        5,
        "store",
        "last_updated"
    ),
    Store(
        2,
        2,
        "art_number1",
        "color1",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        7,
        "store1",
        "last_updated1"
    ),
    Store(
        3,
        3,
        "color2",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        "quantity2",
        12,
        "last_updated2"
    )
)

val reportlistItemObjects: ArrayList<ReportItem> = arrayListOf(
    ReportItem(
        "art_number",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        4,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        4,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number2",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        6,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number3",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        8,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number3",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        8,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number4",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        4,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number4",
        "color2",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        4,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number5",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        3,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number5",
        "color",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        3,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    ),
    ReportItem(
        "art_number2",
        "color2",
        "Events management with onsite access control with QR-codes and Bar-codes and back end access of the database",
        3,
        "store",
        "checked out",
        0,
        0,
        "Collector Name",
        0
    )
)

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
