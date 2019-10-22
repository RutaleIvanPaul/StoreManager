package com.kotlin.ivanpaulrutale.storemanager.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.StoreItem
import com.kotlin.ivanpaulrutale.storemanager.utils.changeFromclassToFragment
import com.kotlin.ivanpaulrutale.storemanager.utils.listItemObjects
import com.kotlin.ivanpaulrutale.storemanager.views.*

class SearchListAdapter: RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var listItems:ArrayList<StoreItem> =
        listItemObjects

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.search_list_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateList(newlist:List<StoreItem>){
        listItems = arrayListOf()
        listItems.addAll(newlist)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = listItems[position]
        holder.art_numberTextView.text = item.art_number
        holder.colorTextView.text = item.color
        holder.descriptionTextView.text = item.description
        holder.quantityTextView.text = item.quantity
        holder.storeTextView.text = item.store
        holder.lastUpdatedTextView.text = item.last_updated

        holder.itemView.setOnClickListener {view ->
            var newCheckOutFragment = CheckOut.newInstance(item.art_number,item.color,item.description,item.store)
            val activity = view.context as AppCompatActivity
            changeFromclassToFragment(
                activity,
                newCheckOutFragment
            )
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val art_numberTextView = itemView.findViewById(R.id.searchlist_art_number) as TextView
        val colorTextView = itemView.findViewById(R.id.searchlist_color) as TextView
        val descriptionTextView = itemView.findViewById(R.id.searchlist_description) as TextView
        val quantityTextView = itemView.findViewById(R.id.searchlist_quantity) as TextView
        val storeTextView = itemView.findViewById(R.id.searchlist_store) as TextView
        val lastUpdatedTextView = itemView.findViewById(R.id.searchlist_last_updated) as TextView
    }

}