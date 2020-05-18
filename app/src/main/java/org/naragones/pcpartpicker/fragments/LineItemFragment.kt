package org.naragones.pcpartpicker.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.naragones.pcpartpicker.R
import org.naragones.pcpartpicker.adapters.MainAdapter
import org.naragones.pcpartpicker.classes.LineItem
import org.naragones.pcpartpicker.databinding.FragmentLineItemBinding
import org.naragones.pcpartpicker.types.PartTypes
import org.naragones.pcpartpicker.types.RequestTypes
import org.naragones.pcpartpicker.viewmodels.MainViewModel

class LineItemFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var requestCode: Int? = null

    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLineItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_line_item, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        requireActivity().run {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            adapter = MainAdapter(
                R.layout.lineitem_cardview,
                viewModel
            )
        }
        binding.adapter = adapter
        requestCode = activity?.intent?.getIntExtra("requestCode", RequestTypes.NULL.requestType)!!
        viewModel.setRequestCode(requestCode!!)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        populateList()
        onLineItemSelected()
    }

    private fun populateList() {
        when (requestCode) {
            RequestTypes.NULL.requestType -> {
                viewModel.localLineItemList?.observe(
                    viewLifecycleOwner,
                    Observer {
                        adapter.setLineItemList(it)
                    }
                )
            }
            RequestTypes.ADD_PARTLIST.requestType -> {
                viewModel.populatePartTypes()
                viewModel.remoteLineItemList.observe(this.viewLifecycleOwner, Observer {
                    adapter.setLineItemList(it)
                })
            }
            RequestTypes.EDIT_PARTLIST.requestType -> {
                viewModel.populatePartTypes()
                if (activity?.intent?.getStringArrayListExtra("existingParts") != null) {
                    viewModel.injectExistingParts(activity?.intent?.getStringArrayListExtra("existingParts") as ArrayList<LineItem?>)
                }

                viewModel.remoteLineItemList.observe(this.viewLifecycleOwner, Observer {
                    adapter.setLineItemList(it)
                })
            }
            RequestTypes.VIEW_PARTLIST.requestType -> {
                activity?.run {
                    viewModel.requestSpecificLineItem(
                        intent.getIntExtra(
                            "lineItem",
                            PartTypes.NULL.partType
                        )
                    )
                        .observe(viewLifecycleOwner, Observer {
                            viewModel.getRemoteByID(it.uuidList)
                        })
                }
                viewModel.remoteLineItemList.observe(viewLifecycleOwner, Observer {
                    adapter.setLineItemList(it)
                })
            }
            RequestTypes.CHOOSE_PART.requestType -> {
                viewModel.remoteLineItemList.observe(this.viewLifecycleOwner, Observer {
                    adapter.setLineItemList(it)
                })
                viewModel.getRemoteByType(
                    PartTypes.values()[requireActivity().intent.getIntExtra(
                        "lineItem",
                        PartTypes.NULL.partType
                    )].name
                )
            }
        }
    }

    private fun onLineItemSelected() {
        viewModel.selected.observe(this.viewLifecycleOwner, Observer { lineItem ->
            if (lineItem != null) {
//                Toast.makeText(
//                    context,
//                    "You selected a " + lineItem.title,
//                    Toast.LENGTH_SHORT
//                ).show()

                val pairToStart: Pair<FragmentActivity, Int>? =
                    viewModel.activityToStart(requireActivity())

                if (pairToStart != null) {
                    activity.run {
                        if (requestCode == RequestTypes.CHOOSE_PART.requestType) {
                            requireActivity().setResult(
                                RequestTypes.CHOOSE_PART.requestType,
                                Intent().putExtra("lineItem", lineItem)
                            )
                            requireActivity().finish()
                        } else {
                            val intent = Intent(activity, pairToStart.first.javaClass)
                            intent.putExtra("requestCode", pairToStart.second)
                            intent.putExtra("lineItem", lineItem.id)

                            startActivityForResult(intent, pairToStart.second)
                        }
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            viewModel.drawSelectedPart(
                resultCode,
                data.getSerializableExtra("lineItem") as LineItem
            )
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LineItemFragment().apply {}
    }
}