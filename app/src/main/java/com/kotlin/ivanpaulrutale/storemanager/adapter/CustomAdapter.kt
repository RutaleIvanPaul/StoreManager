package com.kotlin.ivanpaulrutale.storemanager.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.ListItem
import com.kotlin.ivanpaulrutale.storemanager.views.ItemDetails

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    val listItems:ArrayList<ListItem> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = listItems[position]
        holder.titleTextView.text = item.title

        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, ItemDetails::class.java)
            intent.putExtra("title",item.title)
            intent.putExtra("description",item.description)
            intent.putExtra("image",item.image)
            intent.putExtra("video",item.video)
            view.context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.findViewById(R.id.titleTextView) as TextView
    }

}