package com.ai.tirios.ui.invoice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.databinding.FragmentInvoiceBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class InvoiceFragment : BaseFragment<FragmentInvoiceBinding, InvoiceViewModel>(), InvoiceMedium,
    HasSupportFragmentInjector {

    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set
    var binding: FragmentInvoiceBinding? = null
    lateinit var sharedStorage: SharedStorage
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.invoice = viewModel
        sharedStorage = SharedStorage(requireActivity())
        getData()
    }

    private fun getData() {
        var html = requireArguments().getString("html")
        binding!!.wvPayData.settings.javaScriptEnabled = true
        binding!!.wvPayData.settings.useWideViewPort = true
        binding!!.wvPayData.settings.loadWithOverviewMode = true
        binding!!.wvPayData.settings.builtInZoomControls = true
        binding!!.wvPayData.loadDataWithBaseURL(null, html!!, "text/html", "utf-8", null)
        if(sharedStorage.isLandlord() && requireArguments().getInt("status", -1)==0)
            binding!!.llBtn.visibility = View.VISIBLE
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_invoice
    override lateinit var viewModel: InvoiceViewModel
        @Inject internal set

    override fun onClick(p0: View?) {
    }

    override fun supportFragmentInjector() = mDispatchingAndroidInjector

    override fun newPay() {
        var bundle = Bundle()
        bundle = requireArguments()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.invoiceFragment, true)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.invoice_to_select_payment, bundle, navOptions)
    }

    override fun onBackArrowPressed() {
        requireActivity().onBackPressed()
    }
}