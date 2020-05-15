package org.naragones.pcpartpicker.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.fragments.LineItemFragment
import org.naragones.pcpartpicker.viewmodels.LineItemViewModel


class AddEditPartListActivity : AppCompatActivity() {
    private lateinit var viewModel: LineItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_part_list)

        viewModel = ViewModelProvider(this).get(LineItemViewModel::class.java)
        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        populateData(viewModel)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LineItemFragment.newInstance())
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
            R.id.save_partlist -> {
                Toast.makeText(
                    this,
                    "Saved as [to-do]",
                    Toast.LENGTH_SHORT
                ).show()
                this.setResult(0)
                finish()
                return true
            }
        }
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
