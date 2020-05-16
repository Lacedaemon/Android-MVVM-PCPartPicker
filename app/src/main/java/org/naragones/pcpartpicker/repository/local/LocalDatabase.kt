package org.naragones.pcpartpicker.repository.local

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.naragones.pcpartpicker.classes.LineItem

@Database(entities = [LineItem::class], version = 1, exportSchema = false)
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
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDBAsyncTask(instance).execute()
            }
        }
    }

    private class PopulateDBAsyncTask internal constructor(db: LocalDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val LineItemDao: LineItemDao? = db?.lineItemDao()

        override fun doInBackground(vararg params: Void?): Void? {
            LineItemDao?.insert(
                LineItem(
                    null,
                    "True Jedi",
                    "Build",
                    4000.00,
                    ""
                )
            )
            return null
        }
    }
}