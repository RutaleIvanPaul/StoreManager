package com.kotlin.ivanpaulrutale.storemanager.views


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.R
import com.kotlin.ivanpaulrutale.storemanager.utils.Utils
import kotlinx.android.synthetic.main.fragment_reports.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentReports : Fragment(), com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private var now = Calendar.getInstance()
    private var calendar = Calendar.getInstance()
    private lateinit var dateView : TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
    }

    private fun initializeViews(fragView: View) {
        reports_startdate_txt.setOnClickListener {
            dateView = reports_startdate_txt
            setDate()
        }

        reports_enddate_txt.setOnClickListener {
            dateView = reports_enddate_txt
            setDate()
        }

        get_report_button.setOnClickListener {
            if (reports_startdate_txt.text.toString().isEmpty() || reports_startdate_txt.text.toString().isBlank()) {
                reports_startdate_txt.error = "please provide a start date"
            } else if (reports_enddate_txt.text.toString().isEmpty() || reports_enddate_txt.text.toString().isBlank()) {
                reports_enddate_txt.error = "please provide an end date"
            } else {
                val bundle = bundleOf(
                    "startDate" to reports_startdate_txt.text.toString(),
                    "endDate" to reports_enddate_txt.text.toString(),
                    "store" to store.text.toString(),
                    "color" to color.text.toString(),
                    "artNumber" to art_number.text.toString(),
                    "collector" to collector.text.toString(),
                    "description" to description.text.toString()
                )

                Navigation.findNavController(fragView)
                    .navigate(R.id.action_fragmentReports_to_reportsViewFragment, bundle)
            }
        }
    }

    override fun onDateSet(view: com.wdullaer.materialdatetimepicker.date.DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        now.set(Calendar.YEAR, year)
        now.set(Calendar.MONTH, monthOfYear)
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val selection = Utils.getISODate(now.time)
        view?.dismiss()
        dateView.text = Editable.Factory.getInstance().newEditable(selection)
    }

    private fun setDate() {
        val datePicker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
            this@FragmentReports,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.version = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2
        datePicker.vibrate(true)
        datePicker.setOkColor(Constants.WHITE)
        datePicker.setCancelColor(Constants.WHITE)
        datePicker.show(childFragmentManager, "datePicker")
    }
}
