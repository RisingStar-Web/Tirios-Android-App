package com.ai.tirios.ui.select_payment

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
import com.ai.tirios.databinding.FragmentSelectPaymentBinding
import com.ai.tirios.dataclasses.*
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.utils.Utilities
import javax.inject.Inject

class SelectPaymentFragment : BaseFragment<FragmentSelectPaymentBinding, SelectPaymentViewModel>(),
SelectPaymentMedium{

    var binding: FragmentSelectPaymentBinding? = null
    var sharedStorage: SharedStorage? = null
    val bankAccounts: MutableList<BankAccount> = mutableListOf()
    val landlord_bankAccounts: MutableList<BankAccount> = mutableListOf()
    var selected_account_position = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        binding!!.selectPayment = viewModel
        viewModel.medium = this

        sharedStorage = SharedStorage(requireActivity())
        var linearLayoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager
        binding!!.payingPrice.text = "You are paying $${Utilities.payingPriceFormat(requireArguments().getInt("rent_amount"))}"

        viewModel.getBankAccounts(sharedStorage!!.getid()!!)

        viewModel.bankAccounts.observe(requireActivity(), androidx.lifecycle.Observer {
            bankAccounts.clear()
            it.data.bankAccounts.forEach {
                if (it.customerId != null && it.accountNumber != null){
                    bankAccounts.add(it)
                }
            }
            binding!!.rcView.adapter = SelectPaymentAdapter(requireActivity(),
                bankAccounts, object :AdapterItemClick{
                    override fun onItemClick(position: Int, id: Int, boolean: String) {
                        selected_account_position = position
                    }
                })
        })

        viewModel.responseBankAccounts.observe(requireActivity(), androidx.lifecycle.Observer {
            landlord_bankAccounts.clear()
            landlord_bankAccounts.addAll(it.data.bankAccounts)
            payRent()
        })

        viewModel.responseBankPay.observe(requireActivity(), Observer {
            showToast(resources.getString(R.string.payment_was_successfully))
            var iId=requireArguments().getInt("Id")
            if (requireArguments().containsKey("html"))//after invoice payment is successful
                viewModel.updateInvoiceStatus(UpdateInvoice(0, iId, 1, 1, "Paid", Utilities.currentTimeUtc()))
            else //after rent payment is success
                viewModel.updateRentStatus(UpdateRent(0, requireArguments().getInt("RentInvoiceId"), 1, 1, "Paid"))
        })

        viewModel.responseUpdateInvoice.observe(requireActivity(), Observer {
            onBackPress()
        })

        viewModel.responseUpdateRent.observe(requireActivity(), Observer {
            onBackPress()
        })
    }

    override fun onClick(v: View?) {

    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_select_payment
    override lateinit var viewModel: SelectPaymentViewModel
        @Inject internal set

    override fun onBackPress() {
        requireActivity().onBackPressed()
    }

    override fun Pay() {
        if (requireArguments().containsKey("PayrixMerchantId")){
            payRent()
        }else{
            viewModel.getLandLordBankAccounts(requireArguments().getString("LandlordId")!!)
        }
    }

    fun payRent(){
        lateinit var land_lord_merchant_id : String
        if (requireArguments().containsKey("PayrixMerchantId")){
            land_lord_merchant_id = requireArguments().getString("PayrixMerchantId").toString()
        }else{
            for (i in 0 until landlord_bankAccounts.size){
                if (landlord_bankAccounts.get(i).merchantId != null){
                    land_lord_merchant_id = landlord_bankAccounts.get(i).merchantId
                }
            }
        }
        if (selected_account_position == -1){
            showToast(resources.getString(R.string.please_select_account))
        }else if (landlord_bankAccounts.size == 0 && !requireArguments().containsKey("PayrixMerchantId")){
            showToast(resources.getString(R.string.please_ask_your_landlord_to_add_a_bank_account))
        }else if (bankAccounts.get(selected_account_position).method == 0){
            var bodyPayWithCard = BodyPayWithCard(
                Utilities.currentTimeUtc(),
                Utilities.getLocalIpAddress()!!,
                land_lord_merchant_id,
                bankAccounts.get(selected_account_position).customerId,
                "2",
                bankAccounts.get(selected_account_position).token,
                requireArguments().getInt("rent_amount"),
                "1"
            )
            viewModel.payWithCreditCard(bodyPayWithCard)
        }else {
            var token = Token(
                Customer(sharedStorage!!.getfirstName()!!, sharedStorage!!.getlastName()!!),
                PaymentXX(bankAccounts.get(selected_account_position).method.toString(),
                    bankAccounts.get(selected_account_position).accountNumber,
                    bankAccounts.get(selected_account_position).routing))
            var bodyPayWithBank = BodyPayWithBank(
                Utilities.currentTimeUtc(),
                Utilities.getLocalIpAddress()!!,
                land_lord_merchant_id,
                bankAccounts.get(selected_account_position).customerId,
                "2", token,
                requireArguments().getInt("rent_amount"),
                "7"
            )
            viewModel.payWithBank(bodyPayWithBank)
        }
    }

}
