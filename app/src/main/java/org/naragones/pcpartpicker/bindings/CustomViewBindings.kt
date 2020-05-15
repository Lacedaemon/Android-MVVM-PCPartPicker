package org.naragones.pcpartpicker.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object CustomViewBindings {
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun bindRecyclerViewAdapter(
        recyclerView: RecyclerView,
        toDoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
        println("Entered bindRecyclerViewAdapter")
        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context)
            adapter = toDoAdapter
        }
    }
}