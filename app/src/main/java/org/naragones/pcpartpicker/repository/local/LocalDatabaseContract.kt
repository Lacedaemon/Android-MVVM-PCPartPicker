package org.naragones.pcpartpicker.repository.local

import android.provider.BaseColumns

class LocalDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private fun LocalDatabaseContract() {}

    /* Inner class that defines the table contents */
    object LineItem : BaseColumns {
        const val TABLE_NAME = "local"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_UUID = "uuid"
        const val COLUMN_NAME_UUID_LIST = "uuidList"
    }
}