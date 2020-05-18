package org.naragones.pcpartpicker.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.utils.ListToRoom

@Database(entities = [LineItem::class], version = 1, exportSchema = false)
@TypeConverters(ListToRoom::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun lineItemDao(): LineItemDao?

    companion object {
        @Volatile
        private var instance: LocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java, "local_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
        }
    }
}