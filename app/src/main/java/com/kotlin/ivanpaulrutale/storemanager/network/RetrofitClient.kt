package com.kotlin.ivanpaulrutale.storemanager.network

import com.kotlin.ivanpaulrutale.storemanager.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: EndPoints by lazy {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(EndPoints::class.java)
    }
}