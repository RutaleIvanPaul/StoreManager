package com.kotlin.ivanpaulrutale.storemanager.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlin.ivanpaulrutale.storemanager.models.Store
import com.kotlin.ivanpaulrutale.storemanager.models.Stores

/**
 * Created by Derick W on 20,January,2020
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Database(entities = [Store::class, Stores::class], version = 9, exportSchema = false)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun storeDao() : StoreDao
    abstract fun storesDao() : StoresDao

    companion object {
        @Volatile
        private var dbInstance: StoreDatabase? = null

        fun getDatabase(context: Context) : StoreDatabase {
            val tempInstance =
                dbInstance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,
                    "storeManagerDB"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                dbInstance = instance
                return instance
            }
        }

    }

}
