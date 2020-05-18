package org.naragones.pcpartpicker.classes

import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "local_table")
class LineItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") var subtitle: String,
    @ColumnInfo(name = "price") var price: Double,
    @ColumnInfo(name = "uuid") var uuid: String,
    @ColumnInfo(name = "uuidList") val uuidList: MutableList<String?>?
) : BaseObservable(), Serializable