package com.kotlin.ivanpaulrutale.storemanager.models

import com.google.gson.annotations.SerializedName

class RequestResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("foundItems")
    val storeItems: List<StoreItem>
)

class StoreItem(
    var id: Int = 0,
    var artNumber: String = "",
    var color: String = "",
    var description: String = "",
    var quantity: Int = 0,
    var store: String = "",
    var createdAt: String = "",
    var updatedAt: String = ""
)

class ReportItem(
    val artNumber: String,
    val color: String,
    val description: String,
    val quantity: String,
    val store: String,
    val checkoutTime: String,
    val collector: String
)

class SummarisedReportItem(
    val art_number: String,
    val color: String,
    val description: String,
    var total_quantity: String
)

class SummarisedArtNoReportItem(val art_number: String, var total_quantity: String)

class MonthListItem(val name: String)