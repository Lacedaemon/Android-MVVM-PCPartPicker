package org.naragones.pcpartpicker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.viewholders.RemoteViewHolder
import org.naragones.pcpartpicker.viewmodels.RemoteViewModel

class RemoteAdapter(val layoutID: Int, val viewModel: RemoteViewModel) :
    RecyclerView.Adapter<RemoteViewHolder>() {

    private var lineItemList: List<LineItem>? = null

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RemoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return RemoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemoteViewHolder, position: Int) {
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