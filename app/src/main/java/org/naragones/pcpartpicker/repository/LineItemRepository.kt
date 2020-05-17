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

    fun getSpecific(seqID: Int?): LiveData<LineItem> {
//        DebugASyncTask(lineItemDao).execute()
        println("[Debug] Repo received ID: $seqID")
        return lineItemDao!!.getSpecific(seqID)
    }

    fun getAll(): LiveData<List<LineItem?>?>? {
//        DebugASyncTask(lineItemDao)
        return lineItemDao?.getAll()
    }

    // AsyncTasks to do db operations since they're not allowed on main thread

    private class InsertAsyncTask internal constructor(private val LineItemDao: LineItemDao?) :
        AsyncTask<LineItem?, Void?, Void?>() {
        override fun doInBackground(vararg params: LineItem?): Void? {
            LineItemDao?.insert(params[0])
            return null
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

//        private class DebugASyncTask internal constructor(private val LineItemDao: LineItemDao?) :
//        AsyncTask<LineItem?, Void?, Void?>() {
//        override fun doInBackground(vararg params: LineItem?): Void? {
//            println("[Debug] getAll(): " + DatabaseUtils.dumpCursorToString(LineItemDao?.getCursorAll()))
//            println("[Debug] getSpecificTest() : " + DatabaseUtils.dumpCursorToString(LineItemDao?.getSpecificDebug(2)))
//
//            val specific = LineItemDao?.getSpecificTest(3)
//
////            println("[Debug] specific(): " + specific?.name)
//
//            return null
//        }
//
//    }
}