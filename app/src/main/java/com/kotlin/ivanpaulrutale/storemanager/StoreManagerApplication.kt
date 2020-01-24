package com.kotlin.ivanpaulrutale.storemanager

import android.app.Application
import android.content.Context

/**
 * Created by Derick W on 08,February,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class StoreManagerApplication : Application() {
    companion object {
        lateinit var appContext : Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}
