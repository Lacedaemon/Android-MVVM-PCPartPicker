package org.naragones.pcpartpicker.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.fragments.LineItemFragment
import org.naragones.pcpartpicker.utils.RequestTypes
import org.naragones.pcpartpicker.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, AddEditPartListActivity::class.java)
            intent.putExtra("requestCode", RequestTypes.ADD_PARTLIST.requestType)
            startActivityForResult(intent, RequestTypes.ADD_PARTLIST.requestType)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

//        populateData()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container, LineItemFragment.newInstance()
                )
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        this.viewModel.deleteAll()
        this.viewModel.getAdapter().notifyDataSetChanged()
        Toast.makeText(
            this,
            "Partlists cleared",
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

//    private fun populateData() {
////        val lineItems: MutableList<LineItem> = mutableListOf()
////        val lineItem = LineItem("True Jedi", "", 4000.0)
////        lineItems.add(lineItem)
////        viewModel.getLineItems().value = lineItems
//
//        viewModel.getLineItems()?.observe(this, Observer {
//            viewModel.getAdapter().setLineItemList(it)
//        });
//    }
}
