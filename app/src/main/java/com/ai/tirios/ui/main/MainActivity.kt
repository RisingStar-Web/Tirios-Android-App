package com.ai.tirios.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityMainBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(),
    MainMedium, HasSupportFragmentInjector {

    var binding: ActivityMainBinding? = null
    lateinit var appBarConfiguration: AppBarConfiguration
    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = dataBinding
        binding!!.main = viewModel

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding!!.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_main
    override lateinit var viewModel: MainActivityViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun supportFragmentInjector() = mDispatchingAndroidInjector

}
