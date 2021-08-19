package com.ai.tirios.ui.rented

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.FragmentRentedBinding
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import javax.inject.Inject


class RentedFragment : BaseFragment<FragmentRentedBinding, RentedViewModel>(), RentedMedium {
    var binding: FragmentRentedBinding? = null
    var sharedStorage: SharedStorage? = null
    var adapter: RentedAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        binding!!.rented = viewModel
        viewModel.medium = this
        sharedStorage = SharedStorage(requireActivity())

        adapter = RentedAdapter(requireActivity(), viewModel.properties_array, sharedStorage!!,
            object : AdapterItemClick {
                override fun onItemClick(position: Int, id: Int, boolean: String) {
                    var bundle = Bundle()
                    bundle.putInt("Id", id)
                    propertyListToDetails(bundle)
                }
            })

        var linearLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager
        binding!!.rcView.adapter = adapter

        if(sharedStorage!!.isLandlord())
            binding!!.hasObjects = false
        else
            viewModel.getProperties(sharedStorage!!.getid()!!)

        viewModel.properties_list.observe(requireActivity(), Observer {
            if(it.size == 0) binding!!.hasObjects = false else binding!!.hasObjects = true
            adapter!!.notifyDataSetChanged()
        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_rented
    override lateinit var viewModel: RentedViewModel
        @Inject internal set

    fun propertyListToDetails(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.rentedFragment, false)
            .build()
        if (sharedStorage!!.isLandlord()) {
            Navigation.findNavController(binding!!.root)
                .navigate(R.id.property_list_to_details, bundle, navOptions)
        }else {
            Navigation.findNavController(binding!!.root)
                .navigate(R.id.propertiesList_to_tenantDetails, bundle, navOptions)
        }
    }

    override fun addProperty() {

    }
}
