package org.naragones.pcpartpicker.classes

class LineItem(val name: String, val subtitle: String, val price: Double) {
    private val uuid: String = ""
    val priceString = price.toString()
}