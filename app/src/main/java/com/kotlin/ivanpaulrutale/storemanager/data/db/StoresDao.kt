package com.kotlin.ivanpaulrutale.storemanager.data.db

import androidx.room.*
import com.kotlin.ivanpaulrutale.storemanager.models.Stores

/**
 * Created by Derick W on 25,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Dao
interface StoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStore(stores: Stores) : Long

    @Query("SELECT * from stores")
    fun getAllStores(): MutableList<Stores>

    @Delete
    fun deleteStore(stores: Stores)
}
