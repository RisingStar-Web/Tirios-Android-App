package com.ai.tirios.ui.navigation

import android.os.Bundle
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivityNavigationBinding
import com.ai.tirios.ui.checkout_prices.CheckoutPricesActivity
import com.ai.tirios.ui.landlord_login.LandLordLoginActivity
import com.ai.tirios.ui.signup.SignUpActivity
import com.ai.tirios.base.BaseActivity
import javax.inject.Inject

class NavigationActivity : BaseActivity<ActivityNavigationBinding, NavigationViewModel>(),
    NavigationMedium{

    var binding: ActivityNavigationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_navigation
    override lateinit var viewModel: NavigationViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    fun onClickSignIn(view: View) {
        navigateTo(LandLordLoginActivity::class.java)
    }

    fun onClickCheckOutPrice(view: View) {
        navigateTo(CheckoutPricesActivity::class.java)
    }

    fun onClickSignUp(view: View) {
        navigateTo(SignUpActivity::class.java)
    }
}
