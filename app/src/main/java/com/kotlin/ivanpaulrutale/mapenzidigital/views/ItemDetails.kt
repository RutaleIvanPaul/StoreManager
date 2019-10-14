package com.kotlin.ivanpaulrutale.mapenzidigital.views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.ivanpaulrutale.mapenzidigital.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar_main.*

class ItemDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")
        val video = intent.getStringExtra("video")
        val watchVideoButton: Button = findViewById(R.id.watch_trailer)

        val imageView = findViewById<ImageView>(R.id.itemImage)


        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.descriptionTextView).text = description

        Picasso.with(imageView.context).load(image).into(imageView)

        if (video != ""){
            watchVideoButton.visibility = View.VISIBLE
            watchVideoButton.setOnClickListener {
                val uri: Uri = Uri.parse(video)
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }
        }
    }
}
