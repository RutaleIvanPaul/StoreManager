package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.adapter.SearchListAdapter
import com.kotlin.ivanpaulrutale.storemanager.models.RequestResponse
import com.kotlin.ivanpaulrutale.storemanager.models.Store
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class FragmentSearch : Fragment(), SearchView.OnQueryTextListener {
    private val ART_NUMBER = "artNumber"
    private val COLOR = "color"
    private val DESCRIPTION = "description"
    private var searchFlag = ART_NUMBER

    private lateinit var recyclerViewAdapter: SearchListAdapter
    private var itemList: MutableList<Store> = mutableListOf()

    private lateinit var storeItemsViewModel: StoreItemsViewModel

    override fun onQueryTextSubmit(query: String?): Boolean {
        recyclerViewAdapter.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        recyclerViewAdapter.filter.filter(newText!!)
        return true
    }

    private fun triggerSearch(newText: String) {
        when (searchFlag) {
            ART_NUMBER -> {
                searchItems(artNumber = newText)
            }
            COLOR -> {
                searchItems(color = newText)
            }
            DESCRIPTION -> {
                searchItems(description = newText)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeItemsViewModel = ViewModelProvider(this).get(StoreItemsViewModel::class.java)
        initializeViews(view)
        fetchDbStoredItems()
        observeStoreItemsSaved()
    }

    private fun initializeViews(view: View) {
        // initialize searchview
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        //searchView?.requestFocus()
        searchView?.setOnQueryTextListener(this)

        // initialize swipe refresh
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        swipeRefresh?.setOnRefreshListener {
            fetchItems()
            swipeRefresh.isRefreshing = false
        }

        search_chip_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.search_art_number_chip -> {
                    recyclerViewAdapter.setSearchFlag(Constants.artFlag)
                    recyclerViewAdapter.filter.filter(searchView.query)
                }
                R.id.search_color_chip -> {
                    recyclerViewAdapter.setSearchFlag(Constants.colorFlag)
                    recyclerViewAdapter.filter.filter(searchView.query)
                }
                R.id.search_description_chip -> {
                    recyclerViewAdapter.setSearchFlag(Constants.descriptionFlag)
                    recyclerViewAdapter.filter.filter(searchView.query)
                }
                else -> {
                    recyclerViewAdapter.setSearchFlag(Constants.defaultFlag)
                    recyclerViewAdapter.filter.filter(searchView.query)
                }
            }
        }

        initializeRecyclerView(view)
    }

    private fun initializeRecyclerView(view: View) {
        recyclerViewAdapter = SearchListAdapter(object : SearchListAdapter.ListListener {
            override fun showEmpty() {
                showEmptyItems()
            }

            override fun hideEmpty() {
                hideEmptyItems()
            }

        }, itemList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recycler_view)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun fetchDbStoredItems() {
        if (itemList.isNotEmpty())
            itemList.clear()
        search_progressBar.visibility = View.GONE
        itemList.addAll(fetchStoreItems())
        recyclerViewAdapter.notifyDataSetChanged()
        emptyList()
    }

    private fun emptyList() {
        if (itemList.isEmpty()) {
            showEmptyItems()
        } else {
            hideEmptyItems()
        }
    }

    private fun showEmptyItems() {
        no_items.visibility = View.VISIBLE
        search_recycler_view.visibility = View.GONE
    }

    private fun hideEmptyItems() {
        no_items.visibility = View.GONE
        search_recycler_view.visibility = View.VISIBLE
    }

    private fun fetchItems() {
        search_progressBar.visibility = View.VISIBLE
        RetrofitClient.instance.getItems().enqueue(object : Callback<RequestResponse> {
            override fun onResponse(
                call: Call<RequestResponse>,
                response: Response<RequestResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        search_progressBar.visibility = View.GONE
                        no_items.visibility = View.GONE
                        insertStoreItems(response.body()!!.storeItems as MutableList<Store>)
                    }
                    400 -> {
                        Toast.makeText(activity, "Items not found", Toast.LENGTH_SHORT).show()
                    }
                    404 -> {
                        Toast.makeText(activity, "Items not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                Log.e("FragmentSearch: ", t.message)
            }
        })
    }

    private fun fetchStoreItems() : MutableList<Store> = storeItemsViewModel.getAllItems()

    private fun insertStoreItems(storeItems : MutableList<Store>) {
        storeItemsViewModel.insert(storeItems)
    }

    private fun observeStoreItemsSaved() {
        storeItemsViewModel.savingItems.observe(viewLifecycleOwner, Observer {
            if (it) {
                Handler().postDelayed(
                    {
                        fetchDbStoredItems()
                    }, 1500
                )
            }
        })
    }

    private fun searchItems(artNumber: String = "", color: String = "", description: String = "") {
        if (itemList.isNotEmpty())
            itemList.clear()
        search_progressBar.visibility = View.VISIBLE
        RetrofitClient.instance.searchItems(artNumber, color, description)
            .enqueue(object : Callback<RequestResponse> {
                override fun onResponse(
                    call: Call<RequestResponse>,
                    response: Response<RequestResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            search_progressBar.visibility = View.GONE
                            itemList.addAll(response.body()!!.storeItems as MutableList<Store>)
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                        400 -> {
                            Toast.makeText(activity, "Item not found", Toast.LENGTH_SHORT).show()
                        }
                        404 -> {
                            Toast.makeText(activity, "Item not found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    Log.i("Search", response.toString())
                }

                override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                    Log.e("FragmentSearch: ", t.message)
                }
            })
    }
}
