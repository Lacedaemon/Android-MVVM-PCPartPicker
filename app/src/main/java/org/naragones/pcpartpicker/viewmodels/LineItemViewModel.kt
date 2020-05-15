package org.naragones.pcpartpicker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.classes.LineItems
import org.naragones.pcpartpicker.utils.LineItemAdapter
import org.naragones.pcpartpicker.utils.PartTypes
import org.naragones.pcpartpicker.utils.RequestTypes

class LineItemViewModel : ViewModel() {

    private var adapter: LineItemAdapter = LineItemAdapter(R.layout.partlist_cardview, this)
    private var selected: MutableLiveData<LineItem>? = null
    private val lineItemList: LineItems = LineItems()
    private val _fragmentTitle = MutableLiveData<String>()

    val title: LiveData<String> get() = _fragmentTitle

    fun getAdapter(): LineItemAdapter {
        println("[Debug] Got adapter")
        return this.adapter
    }

    fun setLineItemListInAdapter(lineItemList: List<LineItem>) {
        println("[Debug] Setting Item List in Adapter")
        this.adapter.setLineItemList(lineItemList)
        this.adapter.notifyDataSetChanged()
    }

    fun getPartTypes(): List<PartTypes> {
        return PartTypes.values().asList()
    }

    fun getSelected(): MutableLiveData<LineItem>? {
        return this.selected
    }

    fun getLineItems(): MutableLiveData<MutableList<LineItem>> {
        return lineItemList.lineItems
    }

    fun getLineItemAt(index: Int): LineItem {
        return (if (lineItemList.lineItems.value != null && lineItemList.lineItems.value?.size!! > index
        ) {
            lineItemList.lineItems.value!![index]
        } else null)!!
    }

    fun updateActionBarTitle(requestCode: Int) {
        var str: String = ""
        when (requestCode) {
            RequestTypes.ADD_PARTLIST.requestType -> str = "Add "
            RequestTypes.EDIT_PARTLIST.requestType -> str = "Edit "
            else -> {
                return
            }
        }

        _fragmentTitle.postValue(str + "Partlist")
    }

}
