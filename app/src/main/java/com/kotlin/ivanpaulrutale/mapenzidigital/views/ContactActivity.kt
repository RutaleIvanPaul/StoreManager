package com.kotlin.ivanpaulrutale.mapenzidigital.views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.kotlin.ivanpaulrutale.mapenzidigital.R

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        populateSpinner(findViewById(R.id.spinner),this)

        val twitterButton:Button = findViewById(R.id.twitter)
        twitterButton.setOnClickListener{
            val uri: Uri = Uri.parse("https://twitter.com/MapenziDigital")
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }

        val instagramButton:Button = findViewById(R.id.instagram)
        instagramButton.setOnClickListener{
            val uri: Uri = Uri.parse("https://www.instagram.com/mapenzidigital/")
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }



}
