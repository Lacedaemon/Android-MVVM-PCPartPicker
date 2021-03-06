package org.naragones.pcpartpicker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.repository.local.LineItemDao
import org.naragones.pcpartpicker.repository.local.LocalDatabase

class LineItemRepository(application: Application) {
    private val database: LocalDatabase? =
        LocalDatabase.getInstance(
            application
        )
    private var lineItemDao: LineItemDao? = database?.lineItemDao()

    private val firestore = FirebaseFirestore.getInstance()

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

    fun deleteAll() {
        DeleteAllAsyncTask(
            lineItemDao
        ).execute()
    }

    fun getSpecific(seqID: Int?): LiveData<LineItem> {
        return lineItemDao!!.getSpecific(seqID)
    }

    fun getAll(): LiveData<List<LineItem?>?>? {
        return lineItemDao?.getAll()
    }

    fun getRemoteByType(type: String): Task<QuerySnapshot> {
        return firestore.collection("parts").whereEqualTo("type", type).get()
    }

    fun getRemoteByUUID(uuid: String): Task<DocumentSnapshot> {
        return firestore.collection("parts").document(uuid).get()
    }

    // AsyncTasks to do db operations since they're not allowed on main thread

    private class InsertAsyncTask internal constructor(private val LineItemDao: LineItemDao?) :
        AsyncTask<LineItem?, Void?, Void?>() {
        override fun doInBackground(vararg params: LineItem?): Void? {
            LineItemDao?.insert(params[0])
            return null
        }

    }

    private class UpdateAsyncTask internal constructor(private val LineItemDao: LineItemDao?) :
        AsyncTask<LineItem?, Void?, Void?>() {
        override fun doInBackground(vararg params: LineItem?): Void? {
            LineItemDao?.update(params[0])
            return null
        }

    }

    private class DeleteAllAsyncTask internal constructor(private val LineItemDao: LineItemDao?) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            LineItemDao?.clear()
            return null
        }

    }
}