package org.naragones.pcpartpicker.viewholders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.naragones.pcpartpicker.BR
import org.naragones.pcpartpicker.viewmodels.RemoteViewModel

class RemoteViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: RemoteViewModel, position: Int) {
        binding.setVariable(BR.model, viewModel)
        binding.setVariable(BR.position, position)
        binding.executePendingBindings()
    }
}