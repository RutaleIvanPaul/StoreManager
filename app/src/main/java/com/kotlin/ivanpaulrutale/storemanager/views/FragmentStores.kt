package com.kotlin.ivanpaulrutale.storemanager.views

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.adapter.StoresAdapter
import com.kotlin.ivanpaulrutale.storemanager.models.StoreResponse
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.*
import kotlinx.android.synthetic.main.fragment_stores.*
import kotlinx.android.synthetic.main.fragment_stores.no_items
import kotlinx.android.synthetic.main.fragment_stores.search_progressBar
import kotlinx.android.synthetic.main.fragment_stores.swipe_refresh
import kotlinx.android.synthetic.main.manage_stores.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Derick W on 25,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class FragmentStores : Fragment() {

    private var itemList: MutableList<Stores> = mutableListOf()
    private lateinit var adapter: StoresAdapter
    var serviceInstance = RetrofitClient
    private lateinit var storeItemsViewModel: StoreItemsViewModel

    lateinit var mCallback : SelectionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeItemsViewModel = ViewModelProvider(this).get(StoreItemsViewModel::class.java)
        adapter = StoresAdapter(activity!!.applicationContext, itemList, object : StoresSelection {
            override fun noSelection() {
                edit.remove()
                delete.remove()
            }

            override fun selection() {
                edit.show()
                delete.show()
            }

        })

        val linearLayoutManager = LinearLayoutManager(activity)
        stores_recycler_view.layoutManager = linearLayoutManager
        stores_recycler_view.adapter = adapter
        fetchDbStores()
        observeStoresSaved()

        swipe_refresh.setOnRefreshListener {
            fetchStores()
        }

        add_store.setOnClickListener {
            PasswordBottomSheet.newInstance(object : PasswordListener {
                override fun confirm(value: String) {
                    storeAddition()
                }

            }).show(childFragmentManager, "password_add")
        }

        edit.setOnClickListener {
            PasswordBottomSheet.newInstance(object : PasswordListener {
                override fun confirm(value: String) {
                    storeEdition()
                }

            }).show(childFragmentManager, "password_edit")
        }

        delete.setOnClickListener {
            PasswordBottomSheet.newInstance(object : PasswordListener {
                override fun confirm(value: String) {
                    storeDeletion()
                }

            }).show(childFragmentManager, "password_delete")
        }

    }

    private fun storeDeletion() {
        ManageStoresBottomSheet.newInstance(adapter.getSelectedStore(), Constants.DELETE, object : StoresListener {
            override fun deleteStore(stores: Stores) {
                adapter.getSelectedStore()?.let { it1 -> storeItemsViewModel.deleteStore(it1) }
                showProgress()
                storeDeletion(stores.id)
            }

            override fun addStore(stores: String) {}

            override fun editStore(stores: Stores, value: String) {}

        }).show(childFragmentManager, "stores_delete")
    }

    private fun storeEdition() {
        ManageStoresBottomSheet.newInstance(adapter.getSelectedStore(), Constants.EDIT, object : StoresListener {
            override fun deleteStore(stores: Stores) {}

            override fun addStore(stores: String) {}

            override fun editStore(stores: Stores, value: String) {
                showProgress()
                val map = hashMapOf("store" to value as Any)
                storeEdition(stores.id, map)
            }

        }).show(childFragmentManager, "stores_edit")
    }

    private fun storeAddition() {
        ManageStoresBottomSheet.newInstance(adapter.getSelectedStore(), Constants.ADD, object : StoresListener {
            override fun deleteStore(stores: Stores) {}

            override fun addStore(stores: String) {
                val map = hashMapOf("store" to stores as Any)
                showProgress()
                storeAddition(map)
            }

            override fun editStore(stores: Stores, value: String) {}

        }).show(childFragmentManager, "stores_add")
    }

    private fun fetchDbStores() {
        adapter.resetSelection()
        if (itemList.isNotEmpty())
            itemList.clear()
        search_progressBar.visibility = View.GONE
        itemList.addAll(getStores())
        adapter.notifyDataSetChanged()
        emptyList()
    }

    private fun emptyList() {
        if (itemList.isEmpty()) showEmptyItems() else hideEmptyItems()
    }

    private fun showEmptyItems() {
        no_items.visibility = View.VISIBLE
        stores_recycler_view.remove()
    }

    private fun hideEmptyItems() {
        no_items.visibility = View.GONE
        stores_recycler_view.show()
    }

    private fun showProgress() {
        search_progressBar.show()
        parents.remove()
    }

    private fun hideProgress() {
        search_progressBar.remove()
        parents.show()
    }

    private fun observeStoresSaved() {
        storeItemsViewModel.savingStores.observe(viewLifecycleOwner, Observer {
            if (it) {
                Handler().postDelayed(
                    {
                        fetchDbStores()
                    }, 1500
                )
            }
        })
    }

    private fun getStores() : MutableList<Stores> = storeItemsViewModel.getAllStores()

    private fun fetchStores() {
        showProgress()
        adapter.resetSelection()
        serviceInstance.instance.getStores().enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.d("stores", response.toString())
                when (response.code()) {
                    200 -> {
                        hideProgress()

                        if (swipe_refresh != null)
                            swipe_refresh.isRefreshing = false
                        insertStores(response.body()!!.storeItems as MutableList<Stores>)
                    }
                    400 -> {
                        storeNotFound()
                    }
                    404 -> {
                        storeNotFound()
                    }
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                Log.e("Stores Loading: ", t.message)
            }
        })
    }

    private fun storeNotFound() {
        hideProgress()
        showMessage("Stores not found")
    }

    private fun storeEdition(storeId : Int, map: HashMap<String, Any>) {
        RetrofitClient.instance.editStore(storeId, map).enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.d("stores", response.toString())

                when (response.code()) {
                    200 -> {
                        hideProgress()
                        showMessage("Store edited Successfully.")
                        if (itemList.isNotEmpty())
                            itemList.clear()
                        fetchStores()
                    }
                    else -> {
                        hideProgress()
                        showMessage("Store could not be edited.")
                    }
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                Log.e("StoreEdit:", t.message)
            }
        })
    }

    private fun storeDeletion(storeId : Int) {
        RetrofitClient.instance.deleteStore(storeId).enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.d("stores", response.toString())

                when (response.code()) {
                    200 -> {
                        hideProgress()
                        showMessage("Store deleted Successfully.")
                        if (itemList.isNotEmpty())
                            itemList.clear()
                        fetchStores()
                    }
                    else -> {
                        hideProgress()
                        showMessage("Store could not be deleted.")
                    }
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                Log.e("StoreDelete:", t.message)
            }
        })
    }

    private fun storeAddition(map: HashMap<String, Any>) {
        RetrofitClient.instance.postStore(map).enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.d("stores", response.toString())
                when (response.code()) {
                    200 -> {
                        storeAdded()
                    }
                    201 -> {
                        storeAdded()
                    }
                    else -> {
                        hideProgress()
                        showMessage("Store could not be added.")
                    }
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                Log.e("StoreAddition: ", t.message)
            }
        })
    }

    private fun storeAdded() {
        hideProgress()
        showMessage("Store added successfully.")
        if (itemList.isNotEmpty())
            itemList.clear()
        fetchStores()
    }

    private fun showMessage(message : String) = Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_LONG).show()

    private fun insertStores(stores : MutableList<Stores>) {
        storeItemsViewModel.insertStores(stores)
    }

    interface StoresListener{
        fun addStore(stores: String)
        fun editStore(stores: Stores, value : String)
        fun deleteStore(stores: Stores)
    }

}
