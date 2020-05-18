package org.naragones.pcpartpicker.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.databinding.ActivityAddEditPartlistBinding
import org.naragones.pcpartpicker.fragments.LineItemFragment
import org.naragones.pcpartpicker.types.PartTypes
import org.naragones.pcpartpicker.types.RequestTypes
import org.naragones.pcpartpicker.viewmodels.MainViewModel
import kotlin.properties.Delegates


class AddEditPartListActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var requestCode by Delegates.notNull<Int>()
    private var lineItemID: Int = PartTypes.NULL.partType

    override fun onCreate(savedInstanceState: Bundle?) {
        requestCode = intent!!.getIntExtra(
            "requestCode",
            RequestTypes.NULL.requestType
        )

        lineItemID = intent.getIntExtra("lineItem", PartTypes.NULL.partType)

        super.onCreate(savedInstanceState)
        val activityBinding =
            DataBindingUtil.setContentView<ActivityAddEditPartlistBinding>(
                this,
                R.layout.activity_add_edit_partlist
            )

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fragmentTitle.observe(this, Observer {
            supportActionBar?.title = it
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.updateActionBarTitle(requestCode)

        if (requestCode == RequestTypes.EDIT_PARTLIST.requestType) {
            viewModel.requestSpecificLineItem(lineItemID).observe(this, Observer {
                viewModel.partListTitle.value = it.title
            })
        }

        activityBinding.model = viewModel
        activityBinding.lifecycleOwner = this

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container, LineItemFragment.newInstance()
                )
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_addedit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                val uuid = mutableListOf<String?>()

                viewModel.remoteLineItemList.value!!.forEach {
                    if (it?.uuid ?: String != "") uuid.add(it!!.uuid)
                }

                viewModel.addEditPartList(
                    requestCode,
                    LineItem(lineItemID, viewModel.partListTitle.value!!, "", 0.0, "", uuid)
                )

                Toast.makeText(
                    this,
                    "Saved as '" + viewModel.partListTitle.value + "'",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
                return true
            }
        }

    }
}
