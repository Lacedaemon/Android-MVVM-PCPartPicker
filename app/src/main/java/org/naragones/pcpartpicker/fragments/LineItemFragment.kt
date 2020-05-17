package org.naragones.pcpartpicker.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.databinding.FragmentLineItemBinding
import org.naragones.pcpartpicker.utils.RequestTypes
import org.naragones.pcpartpicker.viewmodels.MainViewModel

class LineItemFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var requestCode: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLineItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_line_item, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.model = viewModel
        requestCode = activity?.intent?.getIntExtra("requestCode", RequestTypes.NULL.requestType)!!
        viewModel.setRequestCode(requestCode!!)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when (requestCode) {
            RequestTypes.NULL.requestType -> {
                viewModel.getLineItems()?.observe(
                    viewLifecycleOwner,
                    Observer {
                        viewModel.setLineItemListInAdapter(it)
                    }
                )
            }
        }
        viewModel.getSelected()?.observe(this.viewLifecycleOwner, Observer { lineItem ->
            if (lineItem != null) {
                Toast.makeText(
                    context,
                    "You selected a " + lineItem.title,
                    Toast.LENGTH_SHORT
                ).show()

                println("[Debug] Activity name: " + activity?.javaClass!!.simpleName)

                val pairToStart: Pair<FragmentActivity, Int>? =
                    viewModel.activityToStart(requireActivity())

                if (pairToStart != null) {
                    println("[Debug] activityToStart: " + pairToStart.first.javaClass.simpleName)

                    activity.run {
                        val intent = Intent(activity, pairToStart.first.javaClass)
                        intent.putExtra("requestCode", pairToStart.second)
                        intent.putExtra("lineItem", lineItem.id)
                        startActivityForResult(intent, pairToStart.second)
                    }
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LineItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LineItemFragment().apply {
            }


    }
}