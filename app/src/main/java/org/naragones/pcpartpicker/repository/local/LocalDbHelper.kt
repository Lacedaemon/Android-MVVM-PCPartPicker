package org.naragones.pcpartpicker.repository.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class LocalDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    var TAG = "com.example.closetvalue::ClosetDBHelper"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_LOCAL_TABLE)
        Log.i(TAG, " in onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase?, i: Int, i1: Int) {
//        db.execSQL(SQL_DELETE_ENTRIES);
        Log.i(TAG, " Upgrading from $i to $i1")
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db.execSQL(SQL_DELETE_ENTRIES);
        Log.i(TAG, " Downgrading from $oldVersion to $newVersion")
    }

    private val CREATE_LOCAL_TABLE =
        "CREATE TABLE " + LocalDatabaseContract.LineItem.TABLE_NAME.toString() + "( " +
                LocalDatabaseContract.LineItem.toString() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LocalDatabaseContract.LineItem.COLUMN_NAME_UUID.toString() + " TEXT, " +
                LocalDatabaseContract.LineItem.COLUMN_NAME_TITLE.toString() + " TEXT, " +
                LocalDatabaseContract.LineItem.COLUMN_NAME_SUBTITLE.toString() + " INTEGER, " +
                LocalDatabaseContract.LineItem.COLUMN_NAME_PRICE.toString() + " REAL, " +
                ")"

    private val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + LocalDatabaseContract.LineItem.TABLE_NAME

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "local.db"
//        val TABLE_NAME = "LOCAL_TABLE"
//        val COLUMN_NAME_UUID = "uuid"
//        val COLUMN_NAME = "username"
    }
}