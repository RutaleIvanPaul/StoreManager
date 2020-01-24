package com.kotlin.ivanpaulrutale.storemanager.views


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.adapter.ReportListAdapter
import com.kotlin.ivanpaulrutale.storemanager.models.GenericResponse
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.ReportsResponse
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.EditListener
import com.kotlin.ivanpaulrutale.storemanager.utils.PDFManagerUtil
import com.kotlin.ivanpaulrutale.storemanager.utils.PasswordListener
import kotlinx.android.synthetic.main.fragment_reports_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ReportsViewFragment : Fragment() {

    var startDate = ""
    var endDate = ""
    var colorFlag = ""
    var descriptionFlag = ""
    var artNumberFlag = ""
    var storeFlag = ""
    var collectorFlag = ""
    val TAG = "ReportsViewFragment: "

    private var itemList = mutableListOf<ReportItem>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.reportsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        if (arguments != null) {
            startDate = arguments!!["startDate"] as String
            endDate = arguments!!["endDate"] as String

            colorFlag = arguments!!["color"] as String
            descriptionFlag = arguments!!["description"] as String
            artNumberFlag = arguments!!["artNumber"] as String
            storeFlag = arguments!!["store"] as String
            collectorFlag = arguments!!["collector"] as String

            clearList()
            fetchReport(startDate, endDate, artNumber = artNumberFlag, color = colorFlag, description = descriptionFlag, store = storeFlag, collector = collectorFlag)
        }
    }

    private fun clearList() {
        if (itemList.isNotEmpty())
            itemList.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.reports_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.getPDF -> {
                getPDF()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getPDF() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            generatePdf(this)
        } else {
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1
            )
        }
    }

    private fun generatePdf(reports: Fragment) {
        ReportFileBottomSheet.newInstance(object : FileNameListener {
            override fun fileName(value: String) {
                PDFManagerUtil.createPdf(reports.activity as Activity, itemList, value)
            }
        }).show(childFragmentManager, "generate_pdf")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            generatePdf(this)

        } else {
            Toast.makeText(activity, "Please Grant Permissions!", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchReport(
        startDate: String = "",
        endDate: String = "",
        artNumber: String = "",
        color: String = "",
        description: String = "",
        store: String = "",
        collector: String = ""
    ) {
        reportsProgressbar.visibility = View.VISIBLE
        RetrofitClient.instance.searchReports(startDate, endDate, artNumber, color, description, store, collector)
            .enqueue(object :
                Callback<ReportsResponse> {
                override fun onResponse(
                    call: Call<ReportsResponse>,
                    response: Response<ReportsResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            reportsProgressbar.visibility = View.GONE
                            for (item in response.body()!!.report) {
                                val reportItem = ReportItem(
                                    item.artNumber,
                                    item.color,
                                    item.description,
                                    item.itemQuantity,
                                    item.store,
                                    item.checkoutTime,
                                    item.itemId,
                                    item.storeId,
                                    item.collector,
                                    item.checkOutQuantity,
                                    item.id
                                )
                                itemList.add(reportItem)
                            }
                            if (itemList.size == 0) {
                                reportsLabel.visibility = View.VISIBLE
                            } else {
                                recyclerView.adapter = ReportListAdapter(object : ReportListAdapter.ListListener {
                                    override fun editCheckOut(item: ReportItem) {
                                        PasswordBottomSheet.newInstance(object : PasswordListener {
                                            override fun confirm(value: String) {
                                                showCheckOutBottomSheet(item)
                                            }

                                        }).show(childFragmentManager, "password")
                                    }

                                }, itemList, activity!!.applicationContext)
                            }
                        }
                        400 -> {
                            reportNotFound()
                        }
                        404 -> {
                            reportNotFound()
                        }
                    }
                }

                override fun onFailure(call: Call<ReportsResponse>, t: Throwable) {
                    reportsLabel.visibility = View.VISIBLE
                    Log.e(TAG, t.message)
                }
            })
    }

    private fun showCheckOutBottomSheet(item : ReportItem) {
        CheckOutEditBottomSheet.newInstance(object : EditListener {
            override fun editCheckIn(id: Int, map: HashMap<String, Any>) {
                checkOutItemEdit(reportsRecyclerView, item.itemId, item.id, map)
            }

        }, item).show(childFragmentManager, "edit_checkout_item")
    }

    private fun reportNotFound() {
        reportsProgressbar.visibility = View.GONE
        reportsLabel.visibility = View.VISIBLE
        Toast.makeText(activity, "Items not found", Toast.LENGTH_LONG).show()
    }

    private fun checkOutItemEdit(view : View, itemID : Int, checkOutID : Int, map: HashMap<String, Any>) {
        RetrofitClient.instance.editItem(itemID, checkOutID, map).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                Log.d("checkouts", response.toString())
                Log.d("checkouts", map.toString())
                when (response.code()) {
                    200 -> {
                        Toast.makeText(activity, "Checkout Item edited Successfully.", Toast.LENGTH_LONG).show()

                        clearList()
                        fetchReport(startDate, endDate, artNumber = artNumberFlag, color = colorFlag, description = descriptionFlag, store = storeFlag, collector = collectorFlag)
                    }
                    else -> {
                        Toast.makeText(
                            activity,
                            "Item could not be edited.",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("CheckoutEdit:", t.message)
            }
        })
    }

    interface FileNameListener {
        fun fileName(value : String)
    }
}
