package com.ai.tirios.ui.maintenance

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.databinding.FragmentMaintenanceBinding
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.listeners.AdapterItemClickForBundle
import com.ai.tirios.ui.maintenance_bot.MaintenanceBotActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MaintenanceFragment : BaseFragment<FragmentMaintenanceBinding, MaintenanceViewModel>(),
    MaintenanceMedium, HasSupportFragmentInjector {

    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set
    var binding: FragmentMaintenanceBinding? = null
    lateinit var sharedStorage: SharedStorage
    var property_id: Int? = null
    var tenant_id: Int? = null
    var properties_array: MutableList<Property> = mutableListOf()
    var address_array = arrayListOf<String>()
    var PayrixMerchantId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.invoice.observe(requireActivity(), Observer {
            var bundle = Bundle()
            bundle.putInt("Id", it.InvoiceId)
            bundle.putInt("status", it.Invoice.PaymentStatus)
            bundle.putString("html", it.DocumentContent)
            bundle.putInt("rent_amount", (it.Amount * 100).toInt())
            bundle.putString("PayrixMerchantId", PayrixMerchantId)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.maintenanceFragment, false)
                .build()
            Navigation.findNavController(binding!!.root)
                .navigate(R.id.maintenance_to_invoice, bundle, navOptions)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.maintenance = viewModel
        sharedStorage = SharedStorage(requireActivity())
        binding!!.hasRequestList = true

        if (sharedStorage.isLandlord())
            binding!!.llBtn.visibility = View.GONE
        else
            binding!!.llBtn.visibility = View.VISIBLE

        var linearLayoutManager_tenant = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager_tenant

        var arrayAdapter = ArrayAdapter(
            requireActivity(), R.layout.spinner_selected_item, address_array
        )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding!!.spinner.adapter = arrayAdapter

        viewModel.landLordProperty.observe(requireActivity(), Observer {
            if (sharedStorage.isLandlord()) {
                properties_array.clear()
                address_array.clear()
                if (it.OwnedProperty.size == 1) {
                    viewModel.getMaintainance(it.OwnedProperty.get(0).Id)
                } else {
                    address_array.add(resources.getString(R.string.select_property))
                    it.OwnedProperty.forEach {
                        address_array.add(it.getAddress())
                    }
                    properties_array.addAll(it.OwnedProperty)
                    arrayAdapter.notifyDataSetChanged()
                }
            } else {
                for (i in 0 until it.TenantProperty.size) {
                    for (j in 0 until it.TenantProperty.get(i).Tenants.size) {
                        if (it.TenantProperty.get(i).Tenants.get(j).UserId.equals(sharedStorage.getid())) {
                            property_id = it.TenantProperty.get(i).Id
                            tenant_id = it.TenantProperty.get(i).Tenants.get(j).TenantId
                            viewModel.getMaintainance(property_id!!)
                            break
                        }
                    }
                }
            }
        })

        viewModel.maintainance.observe(requireActivity(), Observer {
            binding!!.hasRequestList = it.size > 0
            binding!!.rcView.adapter = MaintenanceAdapter(this, it, object :
                AdapterItemClickForBundle {
                override fun onItemClick(position: Int, bundle: Bundle, type: String) {
                    if (type.equals("view", true)) {
                        PayrixMerchantId = bundle.getString("PayrixMerchantId").toString()
                        viewModel.callServiceForInvoice(it.get(position).Invoice.Id)
                    } else {
                        bundle.putString("tenantName", it.get(position).TenantProperty.FirstName+" "+it.get(position).TenantProperty.LastName)
                        bundle.putString("tenantMobile", it.get(position).TenantProperty.Mobile)
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.maintenanceFragment, false)
                            .build()
                        Navigation.findNavController(binding!!.root)
                            .navigate(R.id.maintenance_to_maintenanceDetails, bundle, navOptions)
                    }
                }
            })
        })

        binding!!.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0)
                    viewModel.getMaintainance(properties_array.get(p2 - 1).Id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProperties(sharedStorage.getid()!!)
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_maintenance
    override lateinit var viewModel: MaintenanceViewModel
        @Inject internal set

    override fun onClick(p0: View?) {
    }

    override fun supportFragmentInjector() = mDispatchingAndroidInjector

    override fun newRequest() {
        if(tenant_id != null){
            val bundle = Bundle()
            bundle.putString("tenantId", tenant_id.toString())
            navigateTo(MaintenanceBotActivity::class.java, false, bundle)
        }
    }
}
