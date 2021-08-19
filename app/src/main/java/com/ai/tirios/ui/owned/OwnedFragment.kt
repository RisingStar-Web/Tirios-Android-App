package com.ai.tirios.ui.owned

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.FragmentOwnedBinding
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.google.gson.JsonObject
import javax.inject.Inject

class OwnedFragment : BaseFragment<FragmentOwnedBinding, OwnedViewModel>(), OwnedMedium {

    var binding: FragmentOwnedBinding? = null
    var sharedStorage: SharedStorage? = null
    var adapter: OwnedAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = dataBinding
        viewModel.medium = this
        binding!!.owned = viewModel
        sharedStorage = SharedStorage(requireActivity())

        adapter = OwnedAdapter(requireActivity(), viewModel.properties_array, sharedStorage!!,
            object :AdapterItemClick{
                override fun onItemClick(position: Int, id: Int, from: String) {
                    if (from.equals("RequestActivation")){
                        var jsonObject= JsonObject()
                        jsonObject.addProperty("propertyId", viewModel.properties_array.get(position).Id)
                        jsonObject.addProperty("landlordStatus",
                        if(viewModel.properties_array.get(position).getLandlordActivationStatus()
                                .equals("Approved")) {4}else{1})

                        viewModel.requestActivation(jsonObject)
                    }else{
                        var bundle= Bundle()
                        bundle.putInt("Id", id)
                        propertyListToDetails(bundle)
                    }
                }
            })

        var linearLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager
        binding!!.rcView.adapter = adapter

        viewModel.getProperties(sharedStorage!!.getid()!!)

        viewModel.properties_list.observe(requireActivity(), Observer {
            adapter!!.notifyDataSetChanged()
        })

        viewModel.Property.observe(requireActivity(), Observer {
            viewModel.getProperties(sharedStorage!!.getid()!!)
        })

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_owned
    override lateinit var viewModel: OwnedViewModel
        @Inject internal set

    fun propertyListToDetails(bundle: Bundle) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.ownedFragment, false)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.property_list_to_details, bundle, navOptions)
    }

    override fun addProperty() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.ownedFragment, false)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.propertiesList_to_addProperty, Bundle(), navOptions)
    }
}