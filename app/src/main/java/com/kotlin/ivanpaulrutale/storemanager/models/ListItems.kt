package com.kotlin.ivanpaulrutale.storemanager.models

class StoreItem(val art_number:String,val color:String, val description:String, val quantity:String, val store:String,val last_updated:String)

class ReportItem(val art_number:String,val color:String, val description:String, val quantity:String, val store:String,val checkout_time:String,val collector:String)

class SummarisedReportItem(val art_number:String,val color:String, val description:String, var total_quantity:String)

class SummarisedArtNoReportItem(val art_number:String,var total_quantity:String)

class MonthListItem(val name:String)