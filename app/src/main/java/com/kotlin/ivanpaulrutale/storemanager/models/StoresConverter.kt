package com.kotlin.ivanpaulrutale.storemanager.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Derick W on 22,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class StoresConverter {

    @TypeConverter
    fun stringToStores(json: String): Stores {
        val gson = Gson()
        val type = object : TypeToken<Stores>() {

        }.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun responsesToString(stores: Stores): String {
        val gson = Gson()
        val type = object : TypeToken<Stores>() {

        }.type
        return gson.toJson(stores, type)
    }

}
