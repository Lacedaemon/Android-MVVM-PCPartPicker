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
import org.naragones.pcpartpicker.utils.RequestTypes
import org.naragones.pcpartpicker.viewmodels.MainViewModel


class AddEditPartListActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding: ActivityAddEditPartlistBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_edit_partlist)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.title.observe(this, Observer {
            supportActionBar?.title = it
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.updateActionBarTitle(
            intent!!.getIntExtra(
                "requestCode",
                RequestTypes.NULL.requestType
            )
        )

        activityBinding.model = viewModel

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
            R.id.save_partlist -> {
                viewModel.insert(
                    LineItem(
                        null,
                        viewModel.getPartListTitle(),
                        "Commander in the Grand Army of the Republic",
                        0.0,
                        "Fulcrum"
                    )
                )
                Toast.makeText(
                    this,
                    "Saved as '" + viewModel.getPartListTitle() + "'",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
                return true
            }
        }
        this.setResult(0)
        finish()
        return true
    }
}
