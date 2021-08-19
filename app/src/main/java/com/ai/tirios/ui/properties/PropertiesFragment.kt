package com.ai.tirios.ui.properties

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.FragmentPropertiesBinding
import com.ai.tirios.ui.owned.OwnedFragment
import com.ai.tirios.ui.rented.RentedFragment
import com.google.android.material.tabs.TabLayout
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class PropertiesFragment : BaseFragment<FragmentPropertiesBinding, PropertiesViewModel>(),
    PropertiesMedium, HasSupportFragmentInjector {

    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set
    var binding: FragmentPropertiesBinding? = null
    var sharedStorage: SharedStorage? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.properties = viewModel
        sharedStorage = SharedStorage(requireActivity())

        if(sharedStorage!!.isLandlord()){
            binding!!.tablayout.addTab(binding!!.tablayout.newTab().setText(requireActivity().getString(R.string.owned)))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framelayout, OwnedFragment()).commit()
        }
        binding!!.tablayout.addTab(binding!!.tablayout.newTab().setText(requireActivity().getString(R.string.rented)))

        if(!sharedStorage!!.isLandlord())
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.framelayout, RentedFragment()).commit()


        binding!!.tablayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0){
                    if(sharedStorage!!.isLandlord())
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.framelayout, OwnedFragment()).commit()
                    else
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.framelayout, RentedFragment()).commit()
                }else{
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, RentedFragment()).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_properties
    override lateinit var viewModel: PropertiesViewModel
    @Inject internal set

    override fun supportFragmentInjector()= mDispatchingAndroidInjector

}
