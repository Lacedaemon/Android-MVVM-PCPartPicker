package org.naragones.pcpartpicker.viewmodels

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.naragones.pcpartpicker.activities.PartListInfoActivity
import org.naragones.pcpartpicker.activities.SelectPartActivity
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.repository.LineItemRepository
import org.naragones.pcpartpicker.types.PartTypes
import org.naragones.pcpartpicker.types.RequestTypes


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LineItemRepository(application)
    private val requestCode = MutableLiveData<Int>()
    val localLineItemList = repository.getAll()
    val remoteLineItemList = MutableLiveData<List<LineItem?>?>()
    var fragmentTitle = MutableLiveData<String>()
    var partListTitle = ""
    var selected = MutableLiveData<LineItem>()
    var totalPrice = MutableLiveData<Double>()

    init {
        totalPrice.value = 0.0
    }

    private fun getPartTypes(): List<PartTypes> {
        return PartTypes.values().asList()
    }

    fun getLineItemAt(index: Int): LineItem? {
        return when (requestCode.value) {
            RequestTypes.NULL.requestType -> {
                (if (localLineItemList?.value != null && localLineItemList.value?.size!! > index
                ) {
                    localLineItemList.value!![index]
                } else LineItem(-1, "null", "null", 0.0, "", mutableListOf()))!!
            }
            else -> {
                (if (remoteLineItemList.value != null && remoteLineItemList.value?.size!! > index
                ) {
                    remoteLineItemList.value!![index]
                } else LineItem(-1, "null", "null", 0.0, "", mutableListOf()))!!
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

        fragmentTitle.value = str
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
            "SelectPartActivity" -> return Pair(
                activity, -1
            )
        }

        return null
    }

    fun populatePartTypes() {
        val lineItems: MutableList<LineItem> = mutableListOf()
        var i = 0
        getPartTypes().forEach {
            if (it.name != "NULL") {
                val lineItem = LineItem(it.partType, it.name, "", 0.0, "", mutableListOf())
                lineItems.add(lineItem)
            }
            i++
        }
        remoteLineItemList.value = lineItems
    }

    fun sharePartListTitleWithView(s: CharSequence) {
        partListTitle = s.toString()
    }

    fun addEditPartList(requestCode: Int, lineItem: LineItem) {
        when (requestCode) {
            RequestTypes.ADD_PARTLIST.requestType -> {
                insert(
                    LineItem(
                        null,
                        partListTitle,
                        "Build",
                        0.0,
                        ",",
                        lineItem.uuidList
                    )
                )
            }
            RequestTypes.EDIT_PARTLIST.requestType -> {
                update(
                    LineItem(
                        lineItem.id,
                        partListTitle,
                        "Build",
                        0.0,
                        lineItem.uuid,
                        lineItem.uuidList
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

    fun getRemoteByType(type: String) {
        val lineItems = mutableListOf<LineItem>()
        repository.getRemoteByType(type).addOnSuccessListener { documents ->
            for (document in documents) run {
                val info: HashMap<String, String> = document.data["info"] as HashMap<String, String>

                val finalPrices = getLowestPrice(document.data["prices"] as HashMap<String, Double>)

                val title = info["brand"] + " " + info["series"] + " " + info["model"]
                lineItems.add(
                    LineItem(
                        PartTypes.valueOf(type).partType,
                        title,
                        type,
                        finalPrices.min()!!,
                        document.id,
                        mutableListOf()
                    )
                )
            }
            remoteLineItemList.value = lineItems
        }
    }

    fun getRemoteByID(uuidList: MutableList<String?>?) {
        val lineItems = mutableListOf<LineItem>()
        uuidList?.forEach {
            if (it != null) {
                repository.getRemoteByUUID(it).addOnSuccessListener {
                    val info: HashMap<String, String> =
                        it.data?.get("info") as HashMap<String, String>
                    val finalPrice =
                        getLowestPrice(it.data?.get("prices") as HashMap<String, Double>).min()

                    val title = info["brand"] + " " + info["series"] + " " + info["model"]
                    lineItems.add(
                        LineItem(
                            PartTypes.valueOf(it.get("type") as String).partType,
                            it.get("type") as String,
                            title,
                            finalPrice!!,
                            it.id,
                            mutableListOf()
                        )
                    )

                    totalPrice.value = totalPrice.value?.plus(finalPrice)

                    lineItems.sortBy {
                        it.id
                    }
                    remoteLineItemList.value = lineItems
                }
            }
        }
    }

    private fun getLowestPrice(prices: HashMap<String, Double>): MutableList<Double> {
        val finalPrices = mutableListOf<Double>()
        prices.forEach {
            if (it.value != 0.0) {
                finalPrices.add(it.value)
            }
        }
        return finalPrices
    }

    fun drawSelectedPart(resultCode: Int, selectedPart: LineItem) {
        if (resultCode == RequestTypes.CHOOSE_PART.requestType) {
            val partToBeReplaced = remoteLineItemList.value?.get(selectedPart.id?.minus(1)!!)


            if (partToBeReplaced != null) {
                partToBeReplaced.subtitle = selectedPart.title
                partToBeReplaced.uuid = selectedPart.uuid
                partToBeReplaced.price = selectedPart.price

                remoteLineItemList.value?.toTypedArray()
                    ?.set(selectedPart.id!! - 1, partToBeReplaced)
            }
        }
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
