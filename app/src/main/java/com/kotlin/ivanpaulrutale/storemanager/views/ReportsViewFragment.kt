package com.kotlin.ivanpaulrutale.storemanager.views


import android.Manifest
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
import com.kotlin.ivanpaulrutale.storemanager.models.ReportItem
import com.kotlin.ivanpaulrutale.storemanager.models.ReportsResponse
import com.kotlin.ivanpaulrutale.storemanager.network.RetrofitClient
import com.kotlin.ivanpaulrutale.storemanager.utils.PdfManager
import com.kotlin.ivanpaulrutale.storemanager.utils.reportlistItemObjects
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
    var searchValue = ""
    var searchFlag = ""
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
            searchValue = arguments!!["searchValue"] as String
            searchFlag = arguments!!["searchFlag"] as String
        }
        when (searchFlag) {
            "artNumber" -> fetchReport(startDate, endDate, artNumber = searchValue)
            "color" -> fetchReport(startDate, endDate, color = searchValue)
            "description" -> fetchReport(startDate, endDate, description = searchValue)
        }
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
        PdfManager.createPdf(reports.activity, reportlistItemObjects, "pdf1")
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
        description: String = ""
    ) {
        reportsProgressbar.visibility = View.VISIBLE
        RetrofitClient.instance.searchReports(startDate, endDate, artNumber, color, description)
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
                                    item.collector
                                )
                                itemList.add(reportItem)
                            }
                            if (itemList.size == 0) {
                                reportsLabel.visibility = View.VISIBLE
                            } else {
                                recyclerView.adapter = ReportListAdapter(itemList)
                            }
                        }
                        400 -> {
                            reportsProgressbar.visibility = View.GONE
                            reportsLabel.visibility = View.VISIBLE
                            Toast.makeText(activity, "Items not found", Toast.LENGTH_SHORT).show()
                        }
                        404 -> {
                            reportsProgressbar.visibility = View.GONE
                            reportsLabel.visibility = View.VISIBLE
                            Toast.makeText(activity, "Items not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ReportsResponse>, t: Throwable) {
                    reportsLabel.visibility = View.VISIBLE
                    Log.e(TAG, t.message)
                }
            })
    }
}
