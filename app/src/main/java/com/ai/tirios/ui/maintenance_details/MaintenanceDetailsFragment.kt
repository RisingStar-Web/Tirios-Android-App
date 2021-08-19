package com.ai.tirios.ui.maintenance_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.databinding.FragmentMaintenanceDetailsBinding
import com.ai.tirios.dataclasses.Documents
import com.ai.tirios.listeners.AdapterItemClickForBundle
import com.ai.tirios.ui.image_viewer.ImageViewerActivity
import com.ai.tirios.ui.property_details.PropertyGalleryAdapter
import kotlinx.android.synthetic.main.activity_image_viewer.*
import javax.inject.Inject

class MaintenanceDetailsFragment : BaseFragment<FragmentMaintenanceDetailsBinding, MaintenanceDetailsViewModel>()
    , MaintenanceDetailsMedium{

    var binding: FragmentMaintenanceDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        binding!!.activity = this
        binding!!.maintenanceDetails = viewModel
        viewModel.medium = this

        var linearLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager

        var linearLayoutManagerVideos = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding!!.rcViewVideos.layoutManager = linearLayoutManagerVideos

        viewModel.getMaintainance(requireArguments().getInt("maintenance_request_id"))
        binding!!.tvTenantName.text = requireArguments().getString("tenantName")
        binding!!.tvTenantMobile.text = requireArguments().getString("tenantMobile")
        binding!!.tvVideoLink.text = requireArguments().getString("videoLink")

        viewModel.maintainance.observe(requireActivity(), Observer {
            binding!!.maintenance = it
            var images : MutableList<Documents> = mutableListOf()
            var videos : MutableList<Documents> = mutableListOf()

            if (it.Documents != null){
                it.Documents.forEach {
                    if (it.DocumentExtension.contains("mp4")){
                        videos.add(it)
                    }else {
                        images.add(it)
                    }
                }
                binding!!.rcView.adapter = PropertyGalleryAdapter(requireActivity(), images,
                    object : AdapterItemClickForBundle {
                        override fun onItemClick(position: Int, bundle: Bundle, type: String) {
                            navigateTo(ImageViewerActivity::class.java, false, bundle)
                        }
                    })
                binding!!.rcViewVideos.adapter = PropertyGalleryAdapter(requireActivity(), videos,
                    object : AdapterItemClickForBundle {
                        override fun onItemClick(position: Int, bundle: Bundle, type: String) {
                            navigateTo(ImageViewerActivity::class.java, false, bundle)
                        }
                    })
            }
        })
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    fun matchDetails(inputString: String, whatToFind: String, startIndex: Int = 0): Boolean {
        val matchIndex = inputString.indexOf(whatToFind, startIndex)
        if (matchIndex >= 0) return true else return false
    }

    fun onClickLink() {

        val openURL = Intent(Intent.ACTION_VIEW)
        val videoLink = binding!!.tvVideoLink.text.toString()
        showToast(videoLink)
        if(matchDetails(videoLink, "https://"))
            openURL.data = Uri.parse(videoLink)
        else
            openURL.data = Uri.parse("https://"+videoLink)
        startActivity(openURL)
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_maintenance_details
    override lateinit var viewModel: MaintenanceDetailsViewModel
    @Inject internal set

    override fun onBackArrowPressed() {
        /*val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.maintenanceDetailsFragment, false)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.maintenanceDetails_to_maintenance, Bundle() , navOptions)*/
        requireActivity().onBackPressed()
    }
}