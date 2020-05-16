package org.naragones.pcpartpicker.viewmodels

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.activities.PartListInfoActivity
import org.naragones.pcpartpicker.activities.SelectPartActivity
import org.naragones.pcpartpicker.adapters.RemoteAdapter
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.utils.PartTypes
import org.naragones.pcpartpicker.utils.RequestTypes

class RemoteViewModel(application: Application) : AndroidViewModel(application) {
    private var adapter: RemoteAdapter =
        RemoteAdapter(
            R.layout.lineitem_cardview,
            this
        )
    private var selected: MutableLiveData<LineItem> = MutableLiveData()
    private val lineItemList = MutableLiveData<MutableList<LineItem>>()
    private val _fragmentTitle = MutableLiveData<String>()

    val title: LiveData<String> get() = _fragmentTitle

    fun getAdapter(): RemoteAdapter {
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
        return lineItemList
    }

    fun getLineItemAt(index: Int): LineItem {
        return (if (lineItemList.value != null && lineItemList.value?.size!! > index
        ) {
            lineItemList.value!![index]
        } else null)!!
    }

    fun updateActionBarTitle(requestCode: Int) {
        var str: String
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