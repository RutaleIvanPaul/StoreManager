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
import com.kotlin.ivanpaulrutale.storemanager.utils.hide
import com.kotlin.ivanpaulrutale.storemanager.utils.remove

class ReportListAdapter(private val mCallback : ListListener, private val listItem: List<ReportItem>, var context: Context, var checkStatus : Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val checkOuts = LayoutInflater.from(p0.context).inflate(R.layout.reports_list_items, p0, false)
        val checkIns = LayoutInflater.from(p0.context).inflate(R.layout.report_list_items_checkin, p0, false)
        return if (viewType == 1) CheckInViewHolder(checkIns) else ViewHolder(checkOuts )
    }

    fun updateCheckStatus(value : Int) {
        checkStatus = value
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return listItem[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listItem[position]
        if (checkStatus == 1) {
            holder as CheckInViewHolder

            holder.artNumberTextView.text = context.getString(R.string.report_art_number, item.artNumber)
            holder.colorTextView.text = context.getString(R.string.report_color, item.color)
            holder.storeTextView.text = context.getString(R.string.report_store, item.store)

            holder.checkoutTextView.text = context.getString(R.string.report_check_in, (Utils.getDateFromString(item.checkoutTime, context)))
            holder.quantityTextView.text = context.getString(R.string.report_quantity, item.itemQuantity.toString())
            holder.editCheckOut.remove()
        }
        else {
            holder as ViewHolder

            holder.artNumberTextView.text = context.getString(R.string.report_art_number, item.artNumber)
            holder.colorTextView.text = context.getString(R.string.report_color, item.color)
            holder.storeTextView.text = context.getString(R.string.report_store, item.store)
            holder.collectorTextView.text = context.getString(R.string.report_collector, item.collector)

            holder.editCheckOut.setOnClickListener {
                mCallback.editCheckOut(item)
            }

            holder.checkoutTextView.text = context.getString(
                R.string.report_checkout,
                (Utils.getDateFromString(item.checkoutTime, context))
            )

            holder.quantityTextView.text = context.getString(R.string.report_quantity, item.checkOutQuantity.toString())
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

    class CheckInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artNumberTextView = itemView.findViewById(R.id.reports_view_artnumber_txt) as TextView
        val colorTextView = itemView.findViewById(R.id.reports_view_color_txt) as TextView
        val quantityTextView = itemView.findViewById(R.id.reports_view_quantity_txt) as TextView
        val storeTextView = itemView.findViewById(R.id.reports_view_store_txt) as TextView
        val checkoutTextView = itemView.findViewById(R.id.reports_view_checkout_txt) as TextView
        val editCheckOut = itemView.findViewById(R.id.editCheckOut) as ImageView
    }

    interface ListListener {
        fun editCheckOut(item : ReportItem)
    }
}
