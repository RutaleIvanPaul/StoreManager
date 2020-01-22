package com.kotlin.ivanpaulrutale.storemanager.network

import com.kotlin.ivanpaulrutale.storemanager.models.GenericResponse
import com.kotlin.ivanpaulrutale.storemanager.models.ReportsResponse
import com.kotlin.ivanpaulrutale.storemanager.models.RequestResponse
import com.kotlin.ivanpaulrutale.storemanager.models.StoreResponse
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("items")
    fun getItems(): Call<RequestResponse>

    @GET("items/")
    fun searchItems(
        @Query("artNumber") artNumber: String? = null,
        @Query("color") color: String? = null,
        @Query("description") description: String? = null
    ): Call<RequestResponse>

    @GET("reports/")
    fun searchReports(
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("artNumber") artNumber: String? = null,
        @Query("color") color: String? = null,
        @Query("description") description: String? = null,
        @Query("store") store: String? = null,
        @Query("collector") collector: String? = null
    ): Call<ReportsResponse>

    @POST("items")
    fun checkInItem(@Body body: HashMap<String, Any>): Call<GenericResponse>

    @POST("{id}/checkout")
    fun checkoutItem(@Path("id") id : Int, @Body body: HashMap<String, Any>): Call<GenericResponse>

    @PUT("items")
    fun editItem(@Body body: HashMap<String, Any>): Call<Response>

    @GET("stores")
    fun getStores(): Call<StoreResponse>
}
