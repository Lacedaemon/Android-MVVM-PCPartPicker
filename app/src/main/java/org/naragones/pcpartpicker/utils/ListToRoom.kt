package org.naragones.pcpartpicker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class ListToRoom {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): MutableList<String>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Any?>?>() {}.type
        return gson.fromJson<MutableList<String>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<String>?): String? {
        return gson.toJson(someObjects)
    }
}