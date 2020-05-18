package org.naragones.pcpartpicker.types

enum class RequestTypes(val requestType: Int) {
    NULL(0),
    ADD_PARTLIST(1),
    EDIT_PARTLIST(2),
    VIEW_PARTLIST(3),
    CHOOSE_PART(4)
}