package com.kotlin.ivanpaulrutale.storemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Derick W on 21,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Entity(tableName = "stores", indices = [Index(value = ["id"], unique = true)])
class Stores(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "storeId")
    var storeId: Int = 0,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @ColumnInfo(name = "store")
    @SerializedName("store")
    @Expose
    var store: String = "",

    var selected : Boolean = false
)
