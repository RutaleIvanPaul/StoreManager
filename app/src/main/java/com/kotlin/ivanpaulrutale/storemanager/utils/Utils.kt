package com.kotlin.ivanpaulrutale.storemanager.utils

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.kotlin.ivanpaulrutale.storemanager.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class Utils {

    companion object {
        fun validated(vararg views: View): Boolean {
            var ok = true
            for (v in views) {
                if (v is EditText && TextUtils.isEmpty(v.text.toString())) {
                    ok = false
                    v.error = "Required"
                }
            }
            return ok
        }

        fun getDate(context: Context, date: Date): String {
            val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val year = yearFormat.format(date)

            val monthNameFormat = SimpleDateFormat("MM", Locale.getDefault())
            val monthName = monthNameFormat.format(date)

            val dayOfTheMonthFormat = SimpleDateFormat("dd", Locale.getDefault())
            val day = dayOfTheMonthFormat.format(date)

            return context.getString(R.string.day_space_month_space_year, year, monthName, day)
        }
    }

}
