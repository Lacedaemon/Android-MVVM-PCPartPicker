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

    @Query("SELECT * FROM LOCAL_TABLE")
    fun getAll(): LiveData<List<LineItem?>?>?
}