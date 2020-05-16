package org.naragones.pcpartpicker.classes

import androidx.databinding.BaseObservable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "local_table")
class LineItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val _id: Int? = null,
    @ColumnInfo(name = "title") val name: String,
    @ColumnInfo(name = "subtitle") val subtitle: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "uuid") val uuid: String
) : BaseObservable(),
    Serializable {
    constructor() : this(null, "", "", 0.0, "")
}