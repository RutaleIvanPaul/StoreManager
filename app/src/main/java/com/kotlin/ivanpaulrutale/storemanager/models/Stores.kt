package com.kotlin.ivanpaulrutale.storemanager.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Derick W on 21,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class Stores(
    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("store")
    @Expose
    var store: String = "",

    var selected : Boolean = false
)
