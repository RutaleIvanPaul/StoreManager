package com.kotlin.ivanpaulrutale.storemanager.data.repository

import android.app.Application
import android.os.AsyncTask
import com.kotlin.ivanpaulrutale.storemanager.data.db.StoreDao
import com.kotlin.ivanpaulrutale.storemanager.data.db.StoreDatabase
import com.kotlin.ivanpaulrutale.storemanager.data.db.StoresDao
import com.kotlin.ivanpaulrutale.storemanager.models.Store
import com.kotlin.ivanpaulrutale.storemanager.models.Stores
import io.reactivex.Completable
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class StoreImpl(application: Application) : StoreAbst {

    private var storeDao : StoreDao
    private var storesDao : StoresDao

    init {
        val db = StoreDatabase.getDatabase(application)
        storeDao = db.storeDao()
        storesDao = db.storesDao()
    }

    override fun insertStoreItem(store: Store) = Completable.fromCallable { postItem(store) }

    private fun postItem(store: Store) {
        val insertTask = InsertStoreItemAsyncTask(storeDao)
        insertTask.execute(store)
    }

    override fun getStoreItems(): MutableList<Store> = fetchStoreItems().toMutableList()


    private class InsertStoreItemAsyncTask(dao : StoreDao) :
        AsyncTask<Store, Void, Long>() {

        override fun doInBackground(vararg params: Store): Long? {
            return mAsyncTaskDao.insertStoreItems(params[0])
        }

        private var mAsyncTaskDao: StoreDao = dao
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun fetchStoreItems(): List<Store> {
        val callable: Callable<List<Store>> = Callable { storeDao.getAllStoreItems() }
        val future : Future<List<Store>> = Executors.newSingleThreadExecutor().submit(callable)
        return future.get()
    }



    override fun insertStore(stores: Stores) = Completable.fromCallable { postStore(stores) }

    private fun postStore(stores: Stores) {
        val insertTask = InsertStoresAsyncTask(storesDao)
        insertTask.execute(stores)
    }

    override fun getStores(): MutableList<Stores> = fetchStores().toMutableList()

    private class InsertStoresAsyncTask(dao : StoresDao) :
        AsyncTask<Stores, Void, Long>() {

        override fun doInBackground(vararg params: Stores): Long? {
            return mAsyncTaskDao.insertStore(params[0])
        }

        private var mAsyncTaskDao: StoresDao = dao
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun fetchStores(): List<Stores> {
        val callable: Callable<List<Stores>> = Callable { storesDao.getAllStores() }
        val future : Future<List<Stores>> = Executors.newSingleThreadExecutor().submit(callable)
        return future.get()
    }

    override fun deleteStore(stores: Stores): Completable {
        val deleteTask = DeleteStoreAsyncTask(storesDao)
        return Completable.fromCallable { deleteTask.execute(stores) }
    }

    private class DeleteStoreAsyncTask(dao: StoresDao) :
        AsyncTask<Stores, Void, Void>() {

        override fun doInBackground(vararg params: Stores): Void? {
            mAsyncTaskDao.deleteStore(params[0])
            return null
        }

        private var mAsyncTaskDao: StoresDao = dao

    }

}
