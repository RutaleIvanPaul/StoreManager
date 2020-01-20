package com.kotlin.ivanpaulrutale.storemanager.data.repository

import com.kotlin.ivanpaulrutale.storemanager.models.Store
import io.reactivex.Completable

/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
interface StoreAbst {

    fun insertStoreItem(store: Store) : Completable

    fun getStoreItems() : MutableList<Store>

}
