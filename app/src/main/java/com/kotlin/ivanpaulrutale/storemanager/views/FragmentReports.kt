package com.kotlin.ivanpaulrutale.storemanager.views


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.chip.ChipGroup
import com.kotlin.ivanpaulrutale.storemanager.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentReports : Fragment() {

    private lateinit var startDateTxt: AutoCompleteTextView
    private lateinit var endDateTxt: AutoCompleteTextView
    private lateinit var searchValueEdt: EditText
    private lateinit var reportsButton: Button
    private lateinit var reportsChipGroup: ChipGroup

    var searchFlag = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        initializeViews(view)
        return view
    }

    private fun initializeViews(fragView: View) {
        startDateTxt = fragView.findViewById(R.id.reports_startdate_txt)
        endDateTxt = fragView.findViewById(R.id.reports_enddate_txt)
        searchValueEdt = fragView.findViewById(R.id.reports_search_edt)

        startDateTxt.setOnClickListener {
            setDate(startDateTxt)
        }
        endDateTxt.setOnClickListener {
            setDate(endDateTxt)
        }

        reportsButton = fragView.findViewById(R.id.get_report_button)
        reportsButton.setOnClickListener {
            if (startDateTxt.text.toString().isEmpty() || startDateTxt.text.toString().isBlank()) {
                startDateTxt.error = "please provide a start date"
            } else if (endDateTxt.text.toString().isEmpty() || endDateTxt.text.toString().isBlank()) {
                endDateTxt.error = "please provide an end date"
            } else if (searchValueEdt.text.toString().isEmpty() || searchValueEdt.text.toString().isBlank()) {
                searchValueEdt.error = "please provide a search value"
            } else if (searchFlag.isBlank() || searchFlag.isEmpty()) {
                Toast.makeText(context, "please select a search flag", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = bundleOf(
                    "startDate" to startDateTxt.text.toString(),
                    "endDate" to endDateTxt.text.toString(),
                    "searchValue" to searchValueEdt.text.toString(),
                    "searchFlag" to searchFlag

                )
                Navigation.findNavController(fragView)
                    .navigate(R.id.action_fragmentReports_to_reportsViewFragment, bundle)
            }
        }

        reportsChipGroup = fragView.findViewById(R.id.reports_chip_group)
        reportsChipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.reports_art_number_chip -> {
                    searchFlag = "artNumber"
                }
                R.id.reports_color_chip -> {
                    searchFlag = "color"
                }
                R.id.reports_description_chip -> {
                    searchFlag = "description"
                }
            }
        }
    }

    private fun setDate(textView: AutoCompleteTextView) {
        val cal = Calendar.getInstance()
        val calYear = cal.get(Calendar.YEAR)
        val calMonth = cal.get(Calendar.MONTH)
        val calDay = cal.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                textView.setText(date)
            },
            calYear,
            calMonth,
            calDay
        )
        datePicker.show()
    }
}
