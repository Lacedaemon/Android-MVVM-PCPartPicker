package org.naragones.pcpartpicker.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.viewmodels.LineItemViewModel

class LineItemAdapter(val layoutID: Int, val viewModel: LineItemViewModel) :
    RecyclerView.Adapter<LineItemViewHolder>() {

    private var lineItemList: List<LineItem>? = null

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): LineItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return LineItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LineItemViewHolder, position: Int) {
        //val lineItem: LineItem = lineItemList[position]
        holder.bind(viewModel, position)
    }

    override fun getItemCount(): Int {
        return if (lineItemList == null) 0 else lineItemList!!.size
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutID
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun setLineItemList(list: List<LineItem>) {
        this.lineItemList = list
        lineItemList!!.forEach {
        }
    }


}