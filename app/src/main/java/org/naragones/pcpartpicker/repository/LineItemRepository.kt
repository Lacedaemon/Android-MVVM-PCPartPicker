package org.naragones.pcpartpicker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.repository.local.LineItemDao
import org.naragones.pcpartpicker.repository.local.LocalDatabase

class LineItemRepository(application: Application) {
    val database: LocalDatabase? =
        LocalDatabase.getInstance(
            application
        )
    private var lineItemDao: LineItemDao? = database?.lineItemDao()
    private var all: LiveData<List<LineItem?>?>? = lineItemDao?.getAll()

    // API that repository exposes to outside.
    fun insert(lineItem: LineItem?) {
        InsertAsyncTask(
            lineItemDao
        ).execute(lineItem)
    }

    fun update(lineItem: LineItem?) {
        UpdateAsyncTask(
            lineItemDao
        ).execute(lineItem)
    }

    fun delete(lineItem: LineItem?) {
        DeleteAsyncTask(
            lineItemDao
        ).execute(lineItem)
    }

    fun deleteAll() {
        DeleteAllAsyncTask(
            lineItemDao
        ).execute()
    }

    fun getAll(): LiveData<List<LineItem?>?>? {
        return all
    }

    // AsyncTasks to do db operations since they're not allowed on main thread
    private class InsertAsyncTask internal constructor(LineItemDao: LineItemDao?) :
        AsyncTask<LineItem?, Void?, Void?>() {
        private val LineItemDao: LineItemDao?
        override fun doInBackground(vararg params: LineItem?): Void? {
            LineItemDao?.insert(params[0])
            return null
        }

        init {
            this.LineItemDao = LineItemDao
        }
    }

    private class UpdateAsyncTask internal constructor(LineItemDao: LineItemDao?) :
        AsyncTask<LineItem?, Void?, Void?>() {
        private val LineItemDao: LineItemDao?
        override fun doInBackground(vararg params: LineItem?): Void? {
            LineItemDao?.update(params[0])
            return null
        }

        init {
            this.LineItemDao = LineItemDao
        }
    }

    private class DeleteAsyncTask internal constructor(LineItemDao: LineItemDao?) :
        AsyncTask<LineItem?, Void?, Void?>() {
        private val LineItemDao: LineItemDao?
        override fun doInBackground(vararg params: LineItem?): Void? {
            LineItemDao?.delete(params[0])
            return null
        }

        init {
            this.LineItemDao = LineItemDao
        }
    }

    private class DeleteAllAsyncTask internal constructor(LineItemDao: LineItemDao?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val LineItemDao: LineItemDao?
        override fun doInBackground(vararg params: Void?): Void? {
            LineItemDao?.clear()
            return null
        }

        init {
            this.LineItemDao = LineItemDao
        }
    }
}