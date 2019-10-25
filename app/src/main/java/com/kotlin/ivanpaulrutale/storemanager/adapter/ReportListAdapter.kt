package com.kotlin.ivanpaulrutale.storemanager.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.MonthListItem
import com.kotlin.ivanpaulrutale.storemanager.views.ItemDetails

val listItems_reports:ArrayList<MonthListItem> = arrayListOf(
    MonthListItem("January"),
    MonthListItem("February"),
    MonthListItem("March"),
    MonthListItem("April"),
    MonthListItem("May"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("June"),
    MonthListItem("July"),
    MonthListItem("August"),
    MonthListItem("September"),
    MonthListItem("October"),
    MonthListItem("November"),
    MonthListItem("December")
)


class ReportListAdapter: RecyclerView.Adapter<ReportListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems_reports.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = listItems_reports[position]
        holder.titleTextView.text = item.name

        holder.itemView.setOnClickListener {view ->
            val intent = Intent(view.context, ItemDetails::class.java)
            intent.putExtra("title",item.name)
            view.context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.findViewById(R.id.titleTextView) as TextView
    }

}