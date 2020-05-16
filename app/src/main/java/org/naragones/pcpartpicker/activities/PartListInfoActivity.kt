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
import org.naragones.pcpartpicker.viewmodels.MainViewModel


class PartListInfoActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding: ActivityPartListInfoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_part_list_info)

        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            val intent = Intent(this, AddEditPartListActivity::class.java)
            intent.putExtra("requestCode", RequestTypes.EDIT_PARTLIST.requestType)
            startActivityForResult(intent, RequestTypes.EDIT_PARTLIST.requestType)
        }

        val lineItem = intent.getSerializableExtra("lineItem") as LineItem

        activityBinding.lineItem = lineItem

        supportActionBar?.title = lineItem.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        this.setResult(0)
        finish()
        return true
    }
}
