package com.ai.tirios.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.FragmentHomeBinding
import com.ai.tirios.ui.setting.SettingsActivity
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.ui.notifications.NotificationActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeMedium,
    HasSupportFragmentInjector {

    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set
    var binding: FragmentHomeBinding? = null
    lateinit var sharedStorage: SharedStorage
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.home = viewModel
        sharedStorage = SharedStorage(requireActivity())
        binding!!.profile.setOnClickListener(this)
        binding!!.notifications.setOnClickListener(this)
        binding!!.tvUsername.text = sharedStorage.getfirstName()
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_home
    override lateinit var viewModel: HomeViewModel
        @Inject internal set

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.profile -> {
              navigateTo(SettingsActivity::class.java)
            }
            R.id.notifications -> {
                navigateTo(NotificationActivity::class.java)
            }
        }
    }

    override fun supportFragmentInjector() = mDispatchingAndroidInjector


}