package com.kotlin.ivanpaulrutale.storemanager.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.MonthListItem
import com.kotlin.ivanpaulrutale.storemanager.views.*

class SearchListAdapter: RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var listItems:ArrayList<MonthListItem> = listItemObjects

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateList(newlist:List<MonthListItem>){
        listItems = arrayListOf()
        listItems.addAll(newlist)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = listItems[position]
        holder.titleTextView.text = item.name

        holder.itemView.setOnClickListener {view ->
            var newCheckOutFragment = CheckOut.newInstance(item.name,item.name,item.name,item.name)
            val activity = view.context as AppCompatActivity
            changeFromclassToFragment(activity,newCheckOutFragment)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.findViewById(R.id.titleTextView) as TextView
    }

}