package com.kotlin.ivanpaulrutale.storemanager.utils

import android.view.View

/**
 * Created by Derick W on 24,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}
