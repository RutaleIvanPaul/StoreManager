package com.kotlin.ivanpaulrutale.storemanager.views


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.adapter.ReportListAdapter
import com.kotlin.ivanpaulrutale.storemanager.utils.PdfManager
import com.kotlin.ivanpaulrutale.storemanager.utils.reportlistItemObjects

/**
 * A simple [Fragment] subclass.
 */
class FragmentReports : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.reports_recycler_view)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = ReportListAdapter()

        val getPdf = view.findViewById<Button>(R.id.get_pdf)

        getPdf.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                generatePdf(this,view.findViewById(R.id.reports_layout))
            }
            else{
                this.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
            }
        }

        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            generatePdf(this,view?.findViewById(R.id.reports_layout))

        }
        else{
            Toast.makeText(activity, "Please Grant Permissions!", Toast.LENGTH_LONG).show()
        }
    }

    private fun generatePdf(reports: Fragment,view: View?) {
        PdfManager.createPdf(reports.activity, reportlistItemObjects,"pdf1")
    }
}
