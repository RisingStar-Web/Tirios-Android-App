package com.ai.tirios.ui.tenant_property_details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.FragmentTenantDetailsBinding
import com.ai.tirios.ui.property_details.PropertyGalleryAdapter
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.dataclasses.Tenants
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.listeners.AdapterItemClickForBundle
import com.google.gson.JsonObject
import javax.inject.Inject
import kotlin.math.roundToInt


class TenantDetailsFragment : BaseFragment<FragmentTenantDetailsBinding, TenantDetailsViewModel>(),
    TenantDetailsMedium {

    var binding: FragmentTenantDetailsBinding? = null
    var sharedStorage: SharedStorage? = null
    lateinit var Tenants: Tenants
    lateinit var property: Property

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        binding!!.tenantDetails = viewModel
        viewModel.medium = this
        sharedStorage = SharedStorage(requireActivity())

        var linearLayoutManager_tenant = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager_tenant

        viewModel.getPropertyDetails(requireArguments().getInt("Id", 0).toString())

        viewModel.property_details.observe(requireActivity(), Observer {
            binding!!.property = it
            property = it
            for (i in 0 until it.Tenants.size){
                if (it.Tenants.get(i).UserId.equals(sharedStorage!!.getid())){
                    binding!!.tenant = it.Tenants.get(i)
                    Tenants = it.Tenants.get(i)
                    var bodyTenantAgrement= JsonObject()
                    bodyTenantAgrement.addProperty("documentCategory", 3)
                    bodyTenantAgrement.addProperty("tenantPropertyId", it.Tenants.get(i).UserId)
                    viewModel.getTenantAgrement(it.Tenants.get(i).UserId, bodyTenantAgrement)
                    break
                }
            }
            if (it.Documents != null)
                binding!!.rcView.adapter = PropertyGalleryAdapter(requireActivity(), it.Documents,
                    object : AdapterItemClickForBundle {
                        override fun onItemClick(position: Int, bundle: Bundle, type: String) {

                        }
                    })
        })

        viewModel.lease_agrement.observe(requireActivity(), Observer {
            binding!!.leaseAgrement = it.DocumentURL
        })

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_tenant_details
    override lateinit var viewModel: TenantDetailsViewModel
        @Inject internal set

    override fun payRent() {
        var bundle = Bundle()
        bundle.putInt("Id", requireArguments().getInt("Id", 0))
        bundle.putInt("rent_amount", ((Tenants.Rent * 100).roundToInt()))
        bundle.putString("LandlordId", property.LandlordId)

        for (i in 0 until property.Tenants.size){
            if (property.Tenants.get(i).UserId.equals(sharedStorage!!.getid())){
                bundle.putInt("RentInvoiceId", property.Tenants.get(i).RentInvoiceId)
                break
            }
        }

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.tenantDetailsFragment, false)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.tenantDetails_to_selectPayment, bundle, navOptions)
    }

    override fun onBackPress() {
        /*val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.tenantDetailsFragment, true)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.tenantDetails_to_rentedList, Bundle(), navOptions)*/
        requireActivity().onBackPressed()
    }

}
