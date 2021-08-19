package com.ai.tirios.ui.checkout_prices

import android.os.Bundle
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivityCheckoutPricesBinding
import com.ai.tirios.base.BaseActivity
import javax.inject.Inject

class CheckoutPricesActivity : BaseActivity<ActivityCheckoutPricesBinding, CheckoutPricesViewModel>(),
    CheckoutPricesMedium{

    var binding: ActivityCheckoutPricesBinding? = null

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
        get() = R.layout.activity_checkout_prices
    override lateinit var viewModel: CheckoutPricesViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all
}
