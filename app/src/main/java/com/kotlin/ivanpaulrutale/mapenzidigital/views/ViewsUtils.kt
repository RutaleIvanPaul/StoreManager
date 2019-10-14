package com.kotlin.ivanpaulrutale.mapenzidigital.views

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.kotlin.ivanpaulrutale.mapenzidigital.R
import ug.co.yo.yopay.yopaymentslibrary.YoPay

fun populateSpinner(spinner:Spinner,context:Context) {

    val cityNames = arrayOf("Photography","Videography", "Infographics", "Audio")
    if (spinner != null) {
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, cityNames)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //spinner item selected.
            }
        }
    }
}

val yoAPI: YoPay = YoPay("100118955773","z1gw-ybMI-ptek-jM0O-amJh-VkOJ-TeSt-2NSq")

