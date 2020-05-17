package org.naragones.pcpartpicker.viewmodels

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.activities.PartListInfoActivity
import org.naragones.pcpartpicker.activities.SelectPartActivity
import org.naragones.pcpartpicker.adapters.MainAdapter
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.repository.LineItemRepository
import org.naragones.pcpartpicker.utils.PartTypes
import org.naragones.pcpartpicker.utils.RequestTypes


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var adapter: MainAdapter =
        MainAdapter(
            R.layout.lineitem_cardview,
            this
        )
    private var selected = MutableLiveData<LineItem>()
    private val repository = LineItemRepository(application)
    private val localLineItemList = repository.getAll()
    private val remoteLineItemList = MutableLiveData<List<LineItem?>?>()
    private val requestCode = MutableLiveData<Int>()
    private val _fragmentTitle = MutableLiveData<String>()
    private var _partListTitle: String = ""

    init {
        populateRemoteData()
    }

    fun getAdapter(): MainAdapter {
        return this.adapter
    }

    fun setLineItemListInAdapter(lineItemList: List<LineItem?>?) {
        this.adapter.setLineItemList(lineItemList)
        this.adapter.notifyDataSetChanged()
    }

    private fun getPartTypes(): List<PartTypes> {
        return PartTypes.values().asList()
    }

    fun getSelected(): LiveData<LineItem>? {
        return this.selected
    }

    fun getLineItems(): LiveData<List<LineItem?>?>? {
        return localLineItemList
    }

    fun getLineItemAt(index: Int): LineItem? {
//        println("[Debug] lineItemIndex: " + index)
        return when (requestCode.value) {
            RequestTypes.NULL.requestType -> {
                (if (localLineItemList?.value != null && localLineItemList.value?.size!! > index
                ) {
                    localLineItemList.value!![index]
                } else LineItem(-1, "", "", 0.0, ""))!!
            }
            else -> {
                (if (remoteLineItemList.value != null && remoteLineItemList.value?.size!! > index
                ) {
                    remoteLineItemList.value!![index]
                } else LineItem(-1, "", "", 0.0, ""))!!
            }
        }
    }

    fun updateActionBarTitle(requestCode: Int) {
        val str: String = when (requestCode) {
            RequestTypes.ADD_PARTLIST.requestType -> "Add Partlist"
            RequestTypes.EDIT_PARTLIST.requestType -> "Edit Partlist"
            else -> {
                return
            }
        }

        _fragmentTitle.value = str
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

    private fun populateRemoteData() {
        val lineItems: MutableList<LineItem> = mutableListOf()
        var i = 0
        getPartTypes().forEach {
            if (it.name != "NULL") {
                val lineItem = LineItem(i, it.name, it.partType.toString(), 0.0, "")
                lineItems.add(lineItem)
            }
            i++
        }
        remoteLineItemList.value = lineItems
        setLineItemListInAdapter(lineItems)
    }

    fun sharePartListTitleWithView(s: CharSequence) {
        _partListTitle = s.toString()
    }

    fun getPartListTitle(): String {
        return _partListTitle
    }

    fun getFragmentTitle(): LiveData<String> {
        return _fragmentTitle
    }

    fun addEditPartList(requestCode: Int, lineItem: LineItem) {
        when (requestCode) {
            RequestTypes.ADD_PARTLIST.requestType -> {
                insert(
                    LineItem(
                        null,
                        getPartListTitle(),
                        "Commander in the Grand Army of the Republic",
                        0.0,
                        "Fulcrum"
                    )
                )
            }
            RequestTypes.EDIT_PARTLIST.requestType -> {
                update(
                    LineItem(
                        lineItem.id,
                        getPartListTitle(),
                        "Commander in the Grand Army of the Republic",
                        0.0,
                        "Fulcrum"
                    )
                )
            }
        }
    }

    fun setRequestCode(inboundCode: Int) {
        requestCode.value = inboundCode
    }

    fun requestSpecificLineItem(_id: Int?): LiveData<LineItem> {
        return repository.getSpecific(_id)
    }

    private fun insert(lineItem: LineItem?) {
        repository.insert(lineItem)
    }

    private fun update(lineItem: LineItem?) {
        repository.update(lineItem)
    }

//    fun delete(lineItem: LineItem?) {
//        repository.delete(lineItem)
//    }

    fun deleteAll() {
        repository.deleteAll()
    }

}
