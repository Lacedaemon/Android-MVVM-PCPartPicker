package org.naragones.pcpartpicker.viewholders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.naragones.pcpartpicker.BR
import org.naragones.pcpartpicker.viewmodels.MainViewModel

class MainViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: MainViewModel, position: Int) {
        binding.setVariable(BR.model, viewModel)
        binding.setVariable(BR.position, position)
        binding.executePendingBindings()
    }

}