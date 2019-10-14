//package com.kotlin.ivanpaulrutale.mapenzidigital.views
//
//import com.smarteist.autoimageslider.SliderViewAdapter
//import android.R
//import android.content.Context
//import android.widget.TextView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import com.squareup.picasso.Picasso
//
//
//class SliderAdapter(private val context: Context) :
//    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
//
//    //slider view count could be dynamic size
//    val count: Int
//        get() = 4
//
//    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
//        val inflate =
//            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, null)
//        return SliderAdapterVH(inflate)
//    }
//
//    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
//        viewHolder.textViewDescription.text = "This is slider item $position"
//
//        when (position) {
//            0 -> Picasso.with(viewHolder.itemView.context)
//                .load("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
//                .into(viewHolder.imageViewBackground)
//            1 -> Picasso.with(viewHolder.itemView.context)
//                .load("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
//                .into(viewHolder.imageViewBackground)
//            2 -> Picasso.with(viewHolder.itemView.context)
//                .load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
//                .into(viewHolder.imageViewBackground)
//            else -> Picasso.with(viewHolder.itemView.context)
//                .load("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
//                .into(viewHolder.imageViewBackground)
//        }
//
//    }
//
//    class SliderAdapterVH(var itemView: View) :
//        SliderViewAdapter.ViewHolder(itemView) {
//        var imageViewBackground: ImageView
//        var textViewDescription: TextView
//
//        init {
//            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
//            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
//        }
//    }
//}