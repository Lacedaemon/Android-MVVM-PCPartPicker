package org.naragones.pcpartpicker.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.fragments.LineItemFragment
import org.naragones.pcpartpicker.utils.PartTypes.NULL
import org.naragones.pcpartpicker.utils.PartTypes.values
import org.naragones.pcpartpicker.viewmodels.MainViewModel

class SelectPartActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_part)

        val partType = intent.getIntExtra("lineItem", NULL.partType)

        supportActionBar?.title = values()[partType].name
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
