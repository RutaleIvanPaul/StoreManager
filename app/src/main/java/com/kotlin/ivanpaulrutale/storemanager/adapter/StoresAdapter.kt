package com.kotlin.ivanpaulrutale.storemanager.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.utils.StoresSelection

/**
 * Created by Derick W on 21,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class StoresAdapter(val context: Context, private val listItem: MutableList<Stores>, var mCallback : StoresSelection) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)!!
    private var row : Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Item(inflater.inflate(R.layout.stores, parent, false))
    }

    override fun getItemCount(): Int {
        return listItem.size
    }


    inner class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeName = itemView.findViewById(R.id.storeName) as CheckedTextView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listItem[position]
        holder as Item
        holder.storeName.text = item.store
        holder.storeName.isChecked = item.selected

        if (row == position) {
            item.selected = true
            holder.storeName.setCheckMarkDrawable(R.drawable.check_outline)
            val boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
            holder.storeName.typeface = boldTypeface
        } else {
            item.selected = false
            holder.storeName.setCheckMarkDrawable(0)

            val normalTypeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            holder.storeName.typeface = normalTypeface
        }

        holder.storeName.setOnClickListener {
            row = position
            notifyDataSetChanged()
            mCallback.selection()
        }
    }

    fun getSelectedStore() : Stores? {
        return listItem.find { it.selected }
    }

    fun resetSelection() {
        row = -1
        notifyDataSetChanged()
    }
}
