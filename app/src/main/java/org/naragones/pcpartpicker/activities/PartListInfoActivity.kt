package org.naragones.pcpartpicker.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_part_list_info.*
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.databinding.ActivityPartListInfoBinding
import org.naragones.pcpartpicker.fragments.LineItemFragment
import org.naragones.pcpartpicker.utils.PartTypes
import org.naragones.pcpartpicker.utils.RequestTypes
import org.naragones.pcpartpicker.viewmodels.MainViewModel


class PartListInfoActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var lineItem: Int = PartTypes.NULL.partType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding =
            DataBindingUtil.setContentView<ActivityPartListInfoBinding>(
                this,
                R.layout.activity_part_list_info
            )

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lineItem = intent.getIntExtra("lineItem", PartTypes.NULL.partType)

        fab.setOnClickListener {
            val intent = Intent(this, AddEditPartListActivity::class.java)
            intent.putExtra("requestCode", RequestTypes.EDIT_PARTLIST.requestType)
            intent.putExtra("lineItem", lineItem)
            startActivityForResult(intent, RequestTypes.EDIT_PARTLIST.requestType)
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container, LineItemFragment.newInstance(

                    )
                )
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestSpecificLineItem(lineItem).observe(this, Observer {
            findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = it.title
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        this.setResult(0)
        finish()
        return true
    }
}
