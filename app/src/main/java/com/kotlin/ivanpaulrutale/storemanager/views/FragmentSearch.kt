package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.adapter.SearchListAdapter
import com.kotlin.ivanpaulrutale.storemanager.models.StoreItem

/**
 * A simple [Fragment] subclass.
 */
class FragmentSearch : Fragment(), SearchView.OnQueryTextListener {

    val recyclerViewAdapter: SearchListAdapter = SearchListAdapter()

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchRecyclerView(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchRecyclerView(newText)
        return true
    }

    private fun searchRecyclerView(newText: String?) {
        val userInput = newText?.toLowerCase()
        var newList: ArrayList<StoreItem> = arrayListOf()

        for (item in listItemObjects) {
            if (item.art_number.toLowerCase().contains(userInput as CharSequence)) {
                newList.add(item)
            }
        }
        recyclerViewAdapter.updateList(newList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recycler_view)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = recyclerViewAdapter

        val searchView = view?.findViewById<SearchView>(R.id.searchView)
//        searchView?.requestFocus()
        searchView?.setOnQueryTextListener(this)

        val swipeRefresh = view?.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        swipeRefresh?.setOnRefreshListener {
            refreshSearchItems()
            swipeRefresh.isRefreshing = false
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun refreshSearchItems() {
        //Function to re-fetch new and updated list of items
    }
}
