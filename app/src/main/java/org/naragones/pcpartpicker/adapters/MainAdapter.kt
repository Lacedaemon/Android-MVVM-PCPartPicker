package org.naragones.pcpartpicker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.viewholders.MainViewHolder
import org.naragones.pcpartpicker.viewmodels.MainViewModel

class MainAdapter(private val layoutID: Int, private val viewModel: MainViewModel) :
    RecyclerView.Adapter<MainViewHolder>() {

    private var lineItemList: List<LineItem?>? = null

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    override fun getItemCount(): Int {
        return if (lineItemList == null) 0 else lineItemList!!.size
    }

    private fun getLayoutIdForPosition(): Int {
        return layoutID
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition()
    }

    fun setLineItemList(list: List<LineItem?>?) {
        lineItemList = list

//        lineItemList?.forEach {
//            if (it != null) {
//                println("[Debug] lineItem from adapter: " + it.id + ", " + it.title)
//            }
//        }

        notifyDataSetChanged()
    }


}