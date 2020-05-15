package org.naragones.pcpartpicker.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.fragments.LineItemFragment

class SelectPartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_part)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
}
