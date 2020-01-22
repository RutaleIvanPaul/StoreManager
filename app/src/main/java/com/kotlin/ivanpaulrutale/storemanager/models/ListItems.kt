package com.kotlin.ivanpaulrutale.storemanager.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("foundItems")
    val storeItems: List<Store>
)

class StoreResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("stores")
    val storeItems: List<Stores>
)

class ReportsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("checkoutReport")
    val report: List<ReportItem>
)

class GenericResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("foundItems")
    val storeItems: ItemResponse
)

class ItemResponse(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("collector")
    @Expose
    var collector: String = "",
    @SerializedName("quantity")
    @Expose
    var quantity: Int = 0,
    @SerializedName("itemId")
    @Expose
    var itemID: Int = 0,
    @SerializedName("createdAt")
    @Expose
    var createdAt: String = "",
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String = ""
)

class StoreItems(
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
    val artNumber: String = "",
    val color: String = "",
    val description: String = "",
    val itemQuantity: String = "",
    val store: String = "",
    @SerializedName("createdAt")
    val checkoutTime: String = "",
    val collector: String = ""
)

class SummarisedReportItem(
    val art_number: String,
    val color: String,
    val description: String,
    var total_quantity: String
)

class SummarisedArtNoReportItem(val art_number: String, var total_quantity: String)

class MonthListItem(val name: String)
