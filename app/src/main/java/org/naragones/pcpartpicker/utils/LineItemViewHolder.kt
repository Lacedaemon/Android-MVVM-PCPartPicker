package org.naragones.pcpartpicker.utils

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.naragones.pcpartpicker.BR
import org.naragones.pcpartpicker.viewmodels.LineItemViewModel

class LineItemViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: LineItemViewModel, position: Int) {
        binding.setVariable(BR.model, viewModel)
        binding.setVariable(BR.position, position)
        binding.executePendingBindings()
    }

}