package com.kotlin.ivanpaulrutale.storemanager.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.utils.from_Report_details

class ItemDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val title = intent.getStringExtra("title")


        findViewById<TextView>(R.id.titleTextView).text = title

    }

    override fun onBackPressed() {
        super.onBackPressed()
        from_Report_details = true
    }

    override fun onPause() {
        super.onPause()
        from_Report_details = true
    }
}
