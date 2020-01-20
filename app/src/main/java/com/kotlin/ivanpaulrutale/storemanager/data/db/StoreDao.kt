package com.kotlin.ivanpaulrutale.storemanager.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.ivanpaulrutale.storemanager.models.Store

/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStoreItems(store: Store) : Long

    @Query("SELECT * from store")
    fun getAllStoreItems(): MutableList<Store>

}
