package com.kotlin.ivanpaulrutale.storemanager.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.kotlin.ivanpaulrutale.storemanager.Constants
import com.kotlin.ivanpaulrutale.storemanager.StoreManagerApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val instance: EndPoints by lazy {

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        clientBuilder.readTimeout(60, TimeUnit.SECONDS)

        clientBuilder.addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            when {
                response.code() == 403 -> showMessage("Forbidden Error", StoreManagerApplication.appContext)
                response.code() == 500 -> showMessage("Internal Server Error", StoreManagerApplication.appContext)
                response.code() == 508 -> showMessage("Internal Server Error", StoreManagerApplication.appContext)
                response.code() == 404 -> showMessage("Not Found Error", StoreManagerApplication.appContext)
            }
            response
        }

        clientBuilder.build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(EndPoints::class.java)
    }



    private fun showMessage(message : String, context: Context) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
