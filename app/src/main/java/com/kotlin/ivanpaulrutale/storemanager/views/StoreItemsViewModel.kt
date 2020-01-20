package com.kotlin.ivanpaulrutale.storemanager.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kotlin.ivanpaulrutale.storemanager.data.repository.StoreImpl
import com.kotlin.ivanpaulrutale.storemanager.models.Store
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class StoreItemsViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepository: StoreImpl = StoreImpl(application)
    private var mAllItems: MutableList<Store> = mutableListOf()

    val savingItems = MutableLiveData<Boolean>()
    private var saved = 0

    fun getAllItems(): MutableList<Store> {
        mAllItems = mRepository.getStoreItems()
        return mAllItems
    }

    fun insert(items : MutableList<Store>) {
        saved = 0
        for (item in items) {
            val save = mRepository.insertStoreItem(item)
            save.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    saved++
                    if (saved == items.size)
                        savingItems.value = true
                }
                .doOnError { savingItems.value = false }
                .subscribe()

        }
    }

}
