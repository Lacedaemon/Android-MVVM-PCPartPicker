package org.naragones.pcpartpicker.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import org.naragones.pcpartpicker.classes.LineItem

@Dao
interface LineItemDao {
    @Insert
    fun insert(lineItem: LineItem?)

    @Update
    fun update(lineItem: LineItem?)

    @Delete
    fun delete(lineItem: LineItem?)

    @Query("DELETE from LOCAL_TABLE")
    fun clear()

    @Query("SELECT * FROM LOCAL_TABLE WHERE id = :seqID LIMIT 1")
    fun getSpecific(seqID: Int?): LiveData<LineItem>

    @Query("SELECT * FROM LOCAL_TABLE")
    fun getAll(): LiveData<List<LineItem?>?>?

//    @Query("SELECT * FROM LOCAL_TABLE")
//    open fun getCursorAll(): Cursor?

//    @Query("SELECT * FROM LOCAL_TABLE WHERE id = :id LIMIT 1")
//    fun getSpecificTest(id: Int?): LineItem
//
//    @Query("SELECT * FROM LOCAL_TABLE WHERE id = :id LIMIT 1")
//    fun getSpecificDebug(id: Int?): Cursor?
}