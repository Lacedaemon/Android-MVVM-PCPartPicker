package org.naragones.pcpartpicker.classes

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData

class LineItems : BaseObservable() {

    val lineItems = MutableLiveData<MutableList<LineItem>>()

}