package com.kotlin.ivanpaulrutale.storemanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.utils.Utils

class ReportListAdapter(private val mCallback : ListListener, private val listItem: List<ReportItem>, var context: Context) :
    RecyclerView.Adapter<ReportListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.reports_list_items, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.artNumberTextView.append(item.artNumber)
        holder.colorTextView.append(item.color)
        holder.quantityTextView.append(item.itemQuantity.toString())
        holder.storeTextView.append(item.store)
        holder.checkoutTextView.append(Utils.getDateFromString(item.checkoutTime, context))
        holder.collectorTextView.append(item.collector)

        holder.editCheckOut.setOnClickListener {
            mCallback.editCheckOut(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artNumberTextView = itemView.findViewById(R.id.reports_view_artnumber_txt) as TextView
        val colorTextView = itemView.findViewById(R.id.reports_view_color_txt) as TextView
        val quantityTextView = itemView.findViewById(R.id.reports_view_quantity_txt) as TextView
        val storeTextView = itemView.findViewById(R.id.reports_view_store_txt) as TextView
        val checkoutTextView = itemView.findViewById(R.id.reports_view_checkout_txt) as TextView
        val collectorTextView = itemView.findViewById(R.id.reports_view_collector_txt) as TextView
        val editCheckOut = itemView.findViewById(R.id.editCheckOut) as ImageView
    }

    interface ListListener {
        fun editCheckOut(item : ReportItem)
    }
}
