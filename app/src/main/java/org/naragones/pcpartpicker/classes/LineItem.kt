package org.naragones.pcpartpicker.classes

import androidx.databinding.BaseObservable
import java.io.Serializable

class LineItem(val name: String, val subtitle: String, val price: Double) : BaseObservable(),
    Serializable {
    private val uuid: String = ""
    val priceString = price.toString()
}