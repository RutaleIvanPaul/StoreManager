package com.kotlin.ivanpaulrutale.storemanager.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.models.Store
import java.util.ArrayList

class SearchListAdapter(var mCallback : ListListener, var items : MutableList<Store>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>(), Filterable {

    private var listItems: MutableList<Store> = items
    private var filteredListItems: MutableList<Store> = items
    private var searchFlag = Constants.defaultFlag

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.search_list_item, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return filteredListItems.size
    }

    fun setSearchFlag(value : String) {
        searchFlag = value
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredListItems[position]
        holder.artNumberTextView.text = item.artNumber
        holder.descriptionTextView.text = item.description
        holder.lastUpdatedTextView.text = item.updatedAt

        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf(
                "art_number" to item.artNumber,
                "color" to item.color,
                "description" to item.description,
                "store" to item.store,
                "id" to item.id,
                "quantity" to item.quantity
            )
            Navigation.findNavController(view)
                .navigate(R.id.action_fragmentSearch_to_fragmentCheckOut, bundle)
        }

        holder.editCheckIn.setOnClickListener {
            mCallback.editStoreItem(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artNumberTextView = itemView.findViewById(R.id.searchlist_art_number) as TextView
        val descriptionTextView = itemView.findViewById(R.id.searchlist_description) as TextView
        val lastUpdatedTextView = itemView.findViewById(R.id.searchlist_last_updated) as TextView
        val editCheckIn = itemView.findViewById(R.id.editCheckIn) as ImageView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = listItems.size
                    filterResults.values = listItems
                } else {

                    val search = constraint.toString().toLowerCase()
                    val dataResults = ArrayList<Store>()

                    for (store in listItems) {

                        when(searchFlag) {
                            Constants.artFlag -> {
                                if (store.artNumber.toLowerCase().contains(search)) dataResults.add(store)
                            }
                            Constants.colorFlag -> {
                                if (store.color.toLowerCase().contains(search)) dataResults.add(store)
                            }
                            Constants.descriptionFlag -> {
                                if (store.description.toLowerCase().contains(search)) dataResults.add(store)
                            }
                            else -> {
                                if (store.artNumber.toLowerCase().contains(search) || store.color.toLowerCase().contains(search) || store.description.toLowerCase().contains(search))
                                    dataResults.add(store)
                            }
                        }


                        filterResults.count = dataResults.size
                        filterResults.values = dataResults
                    }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredListItems = results.values as MutableList<Store>
                notifyDataSetChanged()

                if (filteredListItems.isEmpty())
                    mCallback.showEmpty()
                else
                    mCallback.hideEmpty()
            }
        }
    }

    interface ListListener {
        fun showEmpty()
        fun hideEmpty()
        fun editStoreItem(item : Store)
    }
}
