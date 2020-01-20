package com.kotlin.ivanpaulrutale.storemanager.utils

import android.text.TextUtils
import android.view.View
import android.widget.EditText

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
    }

}
