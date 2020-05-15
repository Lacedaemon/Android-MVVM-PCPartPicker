package org.naragones.pcpartpicker.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_part_list_info.*
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.databinding.ActivityPartListInfoBinding
import org.naragones.pcpartpicker.fragments.LineItemFragment
import org.naragones.pcpartpicker.utils.RequestTypes
import org.naragones.pcpartpicker.viewmodels.LineItemViewModel


class PartListInfoActivity : AppCompatActivity() {
    private lateinit var viewModel: LineItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding: ActivityPartListInfoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_part_list_info)

        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            val intent = Intent(this, AddEditPartListActivity::class.java)
            intent.putExtra("requestCode", RequestTypes.EDIT_PARTLIST.requestType)
            startActivityForResult(intent, RequestTypes.EDIT_PARTLIST.requestType)
        }

        val lineItem = intent.getSerializableExtra("lineItem") as LineItem

        activityBinding.lineItem = lineItem

        supportActionBar?.title = lineItem.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(LineItemViewModel::class.java)
        populateData(viewModel)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LineItemFragment.newInstance())
                .commitNow()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        this.setResult(0)
        finish()
        return true
    }

    private fun populateData(viewModel: LineItemViewModel) {
        val lineItems: MutableList<LineItem> = mutableListOf()
        viewModel.getPartTypes().forEach {
            if (it.name != "NULL") {
                val lineItem = LineItem(it.name, it.partType.toString(), 0.0)
                lineItems.add(lineItem)
            }
        }
        viewModel.getLineItems().value = lineItems
    }
}
