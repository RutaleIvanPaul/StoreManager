package com.kotlin.ivanpaulrutale.storemanager.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.StoreItem
import com.kotlin.ivanpaulrutale.storemanager.utils.listItemObjects

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var listItems: ArrayList<StoreItem> =
        listItemObjects

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.search_list_item, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateList(newlist: List<StoreItem>) {
        listItems = arrayListOf()
        listItems.addAll(newlist)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.art_numberTextView.text = item.art_number
        holder.colorTextView.text = item.color
        holder.descriptionTextView.text = item.description
        holder.quantityTextView.text = item.quantity
        holder.storeTextView.text = item.store
        holder.lastUpdatedTextView.text = item.last_updated

        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf(
                "art_number" to item.art_number,
                "color" to item.color,
                "description" to item.description,
                "store" to item.store
            )
            Navigation.findNavController(view)
                .navigate(R.id.action_fragmentSearch_to_fragmentCheckOut, bundle)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val art_numberTextView = itemView.findViewById(R.id.searchlist_art_number) as TextView
        val colorTextView = itemView.findViewById(R.id.searchlist_color) as TextView
        val descriptionTextView = itemView.findViewById(R.id.searchlist_description) as TextView
        val quantityTextView = itemView.findViewById(R.id.searchlist_quantity) as TextView
        val storeTextView = itemView.findViewById(R.id.searchlist_store) as TextView
        val lastUpdatedTextView = itemView.findViewById(R.id.searchlist_last_updated) as TextView
    }

}