package com.ai.tirios.ui.property_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.custom.Symbols
import com.ai.tirios.databinding.FragmentPropertyDetailsBinding
import com.ai.tirios.dataclasses.Tenants
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.listeners.AdapterItemClickForBundle
import com.ai.tirios.ui.add_tenant_bot.AddTenantBotActivity
import com.ai.tirios.ui.maintenance_bot.MaintenanceBotActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class PropertyDetailsFragment :
    BaseFragment<FragmentPropertyDetailsBinding, PropertyDetailsViewModel>(),
    PropertyDetailsMedium {

    var binding: FragmentPropertyDetailsBinding? = null
    var sharedStorage: SharedStorage? = null
    var tenents: MutableList<Tenants> = mutableListOf()
    var updated_tenant_position = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.propertyDetails = viewModel
        sharedStorage = SharedStorage(requireActivity())
        binding!!.isLandlord = sharedStorage!!.isLandlord()

        binding!!.tvMortageAmt.setCurrency(Symbols.USA)
        binding!!.tvPropertyTaxAmt.setCurrency(Symbols.USA)

        var linearLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager

        var linearLayoutManager_tenant = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rcViewTenant.layoutManager = linearLayoutManager_tenant

        viewModel.property_details.observe(requireActivity(), Observer {
            binding!!.property = it

            for (i in 0 until it.Tenants.size){
                if (it.Tenants.get(i).IsResiding){
                    binding!!.tvAddTenant.alpha = 0.3f
                    binding!!.tvAddTenant.isClickable = false
                    break
                }else{
                    binding!!.tvAddTenant.alpha = 1f
                    binding!!.tvAddTenant.isClickable = true
                }
            }

            if (it.Documents != null)
                binding!!.rcView.adapter = PropertyGalleryAdapter(requireActivity(), it.Documents,
                    object : AdapterItemClickForBundle{
                        override fun onItemClick(position: Int, bundle: Bundle, type: String) {

                        }

                    })
            if (it.Tenants != null){
                tenents = it.Tenants
                binding!!.rcViewTenant.adapter = TenantAdapter(requireActivity(), tenents,
                    object: AdapterItemClick{
                        override fun onItemClick(position: Int,id: Int, type: String) {
                            println("=======>"+type)
                            if (type.equals("is_residing", true)){
                                updated_tenant_position = position
                                viewModel.editTenant(position, id)
                            }else if (type.equals("edit", true)){
                                var bundle = Bundle()
                                bundle.putInt("tenant_id", id)
                                bundle.putInt("property_id", requireArguments().getInt("Id", 0))
                                val navOptions = NavOptions.Builder()
                                    .setPopUpTo(R.id.propertyDetailsFragment, false)
                                    .build()
                                Navigation.findNavController(binding!!.root)
                                    .navigate(
                                        R.id.property_details_to_ad_tenant,
                                        bundle,
                                        navOptions
                                    )
                            } else {
                                shortenLongLink(tenents.get(position))
                            }
                        }
                    })
            }
        })

        viewModel.update_tenant.observe(requireActivity(), Observer {
            tenents.set(updated_tenant_position, it)
            binding!!.rcViewTenant.adapter!!.notifyItemChanged(updated_tenant_position)
            for (i in 0 until tenents.size) {
                if (tenents.get(i).IsResiding) {
                    binding!!.tvAddTenant.alpha = 0.6f
                    binding!!.tvAddTenant.isClickable = false
                    break
                } else {
                    binding!!.tvAddTenant.alpha = 1f
                    binding!!.tvAddTenant.isClickable = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPropertyDetails(requireArguments().getInt("Id", 0).toString())
    }

    fun shortenLongLink(tenentData: Tenants) {
        FirebaseApp.initializeApp(requireContext())
        val invitationLink =
            "https://tirios.page.link/?mobile=" + tenentData.Mobile + "&firstName=" + tenentData.FirstName +
                    "&lastName=" + tenentData.LastName + "&tenantID=" + tenentData.TenantId + "&email=" + tenentData.Email //Pass parameters in link as query parameters
        val builder = FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setDomainUriPrefix("https://tirios.page.link")
            .setIosParameters(
                DynamicLink.IosParameters.Builder("com.ai.tirios")
                    .setAppStoreId("1556870687").build()
            ).setAndroidParameters(
                DynamicLink.AndroidParameters.Builder("com.ai.tirios")
                    .setMinimumVersion(1)
                    .build()
            ).setLink(Uri.parse(invitationLink))
        val link = builder.buildDynamicLink()
//        println("============"+link.uri.toString())
        builder.buildShortDynamicLink()
            .addOnSuccessListener { (shortLink, flowchartLink) -> //
                shareLink(shortLink.toString())
//                println("==========>" + shortLink.toString())
            }.addOnFailureListener {
//                println("addOnFailureListener==========>" + it.toString())
            }.addOnCanceledListener {
//                println("==========>on cancel")
            }
    }

    private fun shareLink(link: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_property_details
    override lateinit var viewModel: PropertyDetailsViewModel
        @Inject internal set

    override fun addTenant() {
        var bundle = Bundle()
        bundle.putInt("property_id", requireArguments().getInt("Id", 0))
        navigateTo(AddTenantBotActivity::class.java, false, bundle)
//        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.propertyDetailsFragment, true)
//            .build()
//        Navigation.findNavController(binding!!.root)
//            .navigate(R.id.property_details_to_ad_tenant, bundle, navOptions)
    }

    override fun onBackArrowPressed() {
        /*val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.propertyDetailsFragment, false)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.property_details_to_properties_list, Bundle(), navOptions)*/
        //requireActivity().onBackPressed()
        Navigation.findNavController(binding!!.root).popBackStack()
    }

    override fun editProperty() {
        var bundle = Bundle()
        bundle.putInt("property_id", requireArguments().getInt("Id", 0))
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.propertyDetailsFragment, false)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.propertiesList_to_addProperty, bundle, navOptions)
    }

}

