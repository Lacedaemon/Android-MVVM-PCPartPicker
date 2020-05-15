package org.naragones.pcpartpicker.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.activities.PartListInfoActivity
import org.naragones.pcpartpicker.activities.SelectPartActivity
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.classes.LineItems
import org.naragones.pcpartpicker.utils.LineItemAdapter
import org.naragones.pcpartpicker.utils.PartTypes
import org.naragones.pcpartpicker.utils.RequestTypes

class LineItemViewModel : ViewModel() {

    private var adapter: LineItemAdapter = LineItemAdapter(R.layout.lineitem_cardview, this)
    private var selected: MutableLiveData<LineItem> = MutableLiveData()
    private val lineItemList: LineItems = LineItems()
    private val _fragmentTitle = MutableLiveData<String>()

    val title: LiveData<String> get() = _fragmentTitle

    fun getAdapter(): LineItemAdapter {
        return this.adapter
    }

    fun setLineItemListInAdapter(lineItemList: List<LineItem>) {
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
            RequestTypes.ADD_PARTLIST.requestType -> str = "Add Partlist"
            RequestTypes.EDIT_PARTLIST.requestType -> str = "Edit Partlist"
            else -> {
                return
            }
        }

        _fragmentTitle.postValue(str)
    }

    fun onLineItemClick(index: Int) {
        this.selected.value = getLineItemAt(index)
    }

    fun activityToStart(activity: FragmentActivity): Pair<FragmentActivity, Int>? {
        when (activity.javaClass.simpleName) {
            "MainActivity" -> return Pair(
                PartListInfoActivity(),
                RequestTypes.VIEW_PARTLIST.requestType
            )
            "AddEditPartListActivity" -> return Pair(
                SelectPartActivity(),
                RequestTypes.CHOOSE_PART.requestType
            )
        }

        return null
    }

}
