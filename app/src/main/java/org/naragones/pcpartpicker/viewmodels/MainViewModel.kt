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
    val remoteLineItemList = MutableLiveData<ArrayList<LineItem?>?>()
    var fragmentTitle = MutableLiveData<String>()
    var partListTitle = MutableLiveData<String>()
    var selected = MutableLiveData<LineItem>()
    var totalPrice = MutableLiveData<Double>()

    init {
        totalPrice.value = 0.0
        partListTitle.value = ""
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

    fun onTextChanged(s: CharSequence) {
        partListTitle.value = s.toString()
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
        val lineItems: ArrayList<LineItem?> = arrayListOf()
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

    fun injectExistingParts(existingParts: ArrayList<LineItem?>) {
        existingParts.iterator().forEach {
            if (it != null) {
                remoteLineItemList.value?.set(it.id?.minus(1)!!, it)
                totalPrice.value =
                    totalPrice.value?.plus(remoteLineItemList.value?.get(it.id?.minus(1)!!)?.price!!)
            }
        }
    }

    fun addEditPartList(requestCode: Int, lineItem: LineItem) {
        when (requestCode) {
            RequestTypes.ADD_PARTLIST.requestType -> {
                insert(
                    LineItem(
                        null,
                        partListTitle.value!!,
                        "Build",
                        totalPrice.value!!,
                        ",",
                        lineItem.uuidList
                    )
                )
            }
            RequestTypes.EDIT_PARTLIST.requestType -> {
                update(
                    LineItem(
                        lineItem.id,
                        partListTitle.value!!,
                        "Build",
                        totalPrice.value!!,
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
        val lineItems = arrayListOf<LineItem?>()
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
        totalPrice.value = 0.0
        val lineItems = arrayListOf<LineItem?>()
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
                        it?.id
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
                totalPrice.value = totalPrice.value?.minus(partToBeReplaced.price)
                partToBeReplaced.subtitle = selectedPart.title
                partToBeReplaced.uuid = selectedPart.uuid
                partToBeReplaced.price = selectedPart.price
                totalPrice.value = totalPrice.value?.plus(selectedPart.price)

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

    fun deleteAll() {
        repository.deleteAll()
    }

}
