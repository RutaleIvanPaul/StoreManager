package com.kotlin.ivanpaulrutale.storemanager.models

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Entity(tableName = "store", indices = [Index(value = ["id"], unique = true)])
@TypeConverters(StoresConverter::class)
data class Store(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemID")
    var itemID: Int = 0,

    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "artNumber")
    var artNumber: String = "",

    @ColumnInfo(name = "color")
    var color: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "store")
    var store: String = "",

    @ColumnInfo(name = "createdAt")
    var createdAt: String = "",

    @ColumnInfo(name = "updatedAt")
    var updatedAt: String = "",

    @SerializedName("stores")
    @Expose
    var stores: Stores? = null
)
