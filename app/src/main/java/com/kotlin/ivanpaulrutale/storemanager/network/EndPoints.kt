package com.kotlin.ivanpaulrutale.storemanager.network

import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.models.*
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("${Constants.API_V}items")
    fun getItems(): Call<RequestResponse>

    @GET("${Constants.API_V}items/")
    fun searchItems(
        @Query("artNumber") artNumber: String? = null,
        @Query("color") color: String? = null,
        @Query("description") description: String? = null
    ): Call<RequestResponse>

    @GET("${Constants.API_V}reports/")
    fun searchReports(
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("artNumber") artNumber: String? = null,
        @Query("color") color: String? = null,
        @Query("description") description: String? = null,
        @Query("store") store: String? = null,
        @Query("collector") collector: String? = null
    ): Call<ReportsResponse>

    @GET("${Constants.API_V}reports/checkout")
    fun checkOutReports(
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("artNumber") artNumber: String? = null,
        @Query("color") color: String? = null,
        @Query("description") description: String? = null,
        @Query("store") store: String? = null,
        @Query("collector") collector: String? = null
    ): Call<ReportsResponse>

    @GET("${Constants.API_V}reports/checkin")
    fun checkInReports(
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("artNumber") artNumber: String? = null,
        @Query("color") color: String? = null,
        @Query("description") description: String? = null,
        @Query("store") store: String? = null,
        @Query("collector") collector: String? = null
    ): Call<CheckInReportsResponse>

    @POST("${Constants.API_V}items")
    fun checkInItem(@Body body: HashMap<String, Any>): Call<GenericResponse>

    @POST("${Constants.API_V}{id}/checkout")
    fun checkoutItem(@Path("id") id : Int, @Body body: HashMap<String, Any>): Call<GenericResponse>

    @PUT("${Constants.API_V}{itemId}/checkout/{checkoutId}")
    fun editItem(@Path("itemId") itemId : Int, @Path("checkoutId") checkoutId : Int, @Body body: HashMap<String, Any>): Call<GenericResponse>

    @PUT("${Constants.API_V}items/{id}")
    fun editItemCheckIn(@Path("id") id : Int, @Body body: HashMap<String, Any>): Call<GenericResponse>

    @GET("${Constants.API_V}stores")
    fun getStores(): Call<StoreResponse>

    @PUT("${Constants.API_V}stores/{id}")
    fun editStore(@Path("id") id : Int, @Body body: HashMap<String, Any>): Call<StoreResponse>

    @DELETE("${Constants.API_V}stores/{id}")
    fun deleteStore(@Path("id") id : Int): Call<StoreResponse>

    @POST("${Constants.API_V}stores")
    fun postStore(@Body body: HashMap<String, Any>): Call<StoreResponse>
}
