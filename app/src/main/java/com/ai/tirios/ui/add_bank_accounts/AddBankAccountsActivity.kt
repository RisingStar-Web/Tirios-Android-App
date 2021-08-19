package com.ai.tirios.ui.add_bank_accounts

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityAddBankAccountsBinding
import com.ai.tirios.dataclasses.*
import com.ai.tirios.utils.Utilities
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


class AddBankAccountsActivity : BaseActivity<ActivityAddBankAccountsBinding, AddBankAccountsViewModel>(),
    AddBankAccountsMedium{

    var sharedStorage: SharedStorage? = null
    var binding: ActivityAddBankAccountsBinding? = null
    var accountType = 0
    var et_ssn_text = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = dataBinding
        binding!!.addBankAccounts = viewModel
        binding!!.activity = this
        viewModel.medium = this
        sharedStorage = SharedStorage(this)
        binding!!.isLandlord = sharedStorage!!.isLandlord()

        var arrayAdapter = ArrayAdapter(
            this, R.layout.spinner_selected_item, resources.getStringArray(R.array.account_type))
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding!!.spinner.adapter = arrayAdapter

        viewModel.merchant.observe(this, Observer {
            addCustomerBody()
        })

        viewModel.customer.observe(this, Observer {
            var payment = Payment(accountType,
                binding!!.etAccountNumber.text.toString(),
                binding!!.etRoutingNumber.text.toString())
            var bodyBankAccountTokenization = BodyBankAccountTokenization(it.data.customerId, payment)
            viewModel.getBankAccountToken(bodyBankAccountTokenization)
        })

        viewModel.bankAccountTokenization.observe(this, Observer {
            finish()
        })

        binding!!.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 1){
                    accountType = 8
                }else if (p2 == 2){
                    accountType = 9
                }else if(p2 == 0){
                    accountType = 0
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        })

    }

    override fun onStart() {
        super.onStart()
        setTextWatcherSsnNumber()
    }

    private fun setTextWatcherSsnNumber() {
        binding?.etSsn?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding?.etSsn?.setOnKeyListener { it, keyCode, event ->
                    if (keyCode != KeyEvent.KEYCODE_DEL) {
                        val len: Int = binding?.etSsn?.text!!.length
                        if (len == 3) {
                            binding?.etSsn?.setText(binding?.etSsn?.text.toString() + "-")
                            binding?.etSsn?.text?.length?.let { it1 ->
                                binding?.etSsn?.setSelection(
                                    it1
                                )
                            }
                        }
                        if (len == 6) {
                            binding?.etSsn?.setText(binding?.etSsn?.text.toString() + "-")
                            binding?.etSsn?.text?.let { it1 -> binding?.etSsn?.setSelection(it1.length) }
                        }
                    }
                    false
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        binding?.etSsn?.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_add_bank_accounts
    override lateinit var viewModel: AddBankAccountsViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    fun addMerchantBody(){
        var accounts : MutableList<Account> = mutableListOf()
        var account = Account(
            AccountX(
                accountType,
                binding!!.etAccountNumber.text.toString(),
                binding!!.etRoutingNumber.text.toString()),"USD", "1")
        accounts.add(account)

        var members: MutableList<Member> = mutableListOf()
        members.add(Member(
            intent.extras!!.getString("Street").toString(),
            intent.extras!!.getString("City").toString(),
            "USA", sharedStorage!!.getemail()!!, sharedStorage!!.getfirstName()!!,
            sharedStorage!!.getlastName()!!, 0, "1",
            binding!!.etSsn.text.toString().replace("-",""),
            intent.extras!!.getString("State").toString(),
            "CEO", intent.extras!!.getString("ZipCode").toString()
        ))

        var merchant = Merchant("1799", members, 1)

        var bodyAddMerchant=  BodyAddMerchant(
            accounts, intent.extras!!.getString("Street").toString(),
            intent.extras!!.getString("City").toString(),
            "USA","USD", Utilities.currentTimeUtc(),
            sharedStorage!!.getemail()!!, Utilities.getLocalIpAddress()!!,
            merchant, sharedStorage!!.getfirstName()+" "+ sharedStorage!!.getlastName(),
            sharedStorage!!.getusername()!!.replace("+","") ,
            intent.extras!!.getString("State").toString(),
            sharedStorage!!.getid()!!, 2,
            intent.extras!!.getString("ZipCode").toString()
        )

        viewModel.addMerchant(bodyAddMerchant)
    }

    fun addCustomerBody(){
        var customerDetail = CustomerDetail(
            intent.extras!!.getString("Street").toString(),
            intent.extras!!.getString("City").toString(),
            "USA",
            sharedStorage!!.getemail()!!,sharedStorage!!.getfirstName()!!,
            sharedStorage!!.getlastName()!!,
            sharedStorage!!.getusername()!!.replace("+",""),
            intent.extras!!.getString("State").toString(),
            intent.extras!!.getString("ZipCode").toString()
        )
        var bodyAddCustomer = BodyAddCustomer(
            customerDetail,
            Utilities.currentTimeUtc(),
            Utilities.getLocalIpAddress()!!,
            sharedStorage!!.getid()!!
        )
        viewModel.addCustomer(bodyAddCustomer)
    }

    override fun addAccount() {
        if(accountType == 0 &&
            TextUtils.isEmpty(binding!!.etAccountNumber.text.toString()) &&
            TextUtils.isEmpty(binding!!.etConfirmAccountNumber.text.toString()) &&
            TextUtils.isEmpty(binding!!.etRoutingNumber.text.toString()) &&
            TextUtils.isEmpty(binding!!.etAccountHolderName.text.toString()) &&
            TextUtils.isEmpty(binding!!.etSsn.text.toString()) &&
            !binding!!.cbTerms.isChecked ){
            showAlert(resources.getString(R.string.please_enter_all_the_fields_and_check))
        }else if (accountType == 0){
            showAlert(resources.getString(R.string.please_select_a_bank_account_type))
        }else if (TextUtils.isEmpty(binding!!.etAccountNumber.text.toString())){
            showAlert(resources.getString(R.string.please_add_the_bank_account))
        }else if (TextUtils.isEmpty(binding!!.etConfirmAccountNumber.text.toString())){
            showAlert(resources.getString(R.string.please_confirm_account_number))
        }else if (!binding!!.etAccountNumber.text.toString().equals(binding!!.etConfirmAccountNumber.text.toString())){
            showAlert(resources.getString(R.string.account_number_does_not_match))
        }else if (TextUtils.isEmpty(binding!!.etRoutingNumber.text.toString())){
            showAlert(resources.getString(R.string.please_add_the_routing_Number))
        }else if (binding!!.etRoutingNumber.text.toString().length != 9){
            showAlert(resources.getString(R.string.please_enter_a_valid_nine_digit_routing_number))
        }else if (TextUtils.isEmpty(binding!!.etAccountHolderName.text.toString())){
            showAlert(resources.getString(R.string.please_enter_account_holder_name))
        }else if (TextUtils.isEmpty(binding!!.etSsn.text.toString()) && sharedStorage!!.isLandlord()){
            showAlert(resources.getString(R.string.please_enter_the_ssn))
        }else if (binding!!.etSsn.text.toString().replace("-","").length != 9 && sharedStorage!!.isLandlord()){
            showAlert(resources.getString(R.string.please_enter_valid_ssn))
        }else if (!binding!!.cbTerms.isChecked){
            showAlert(resources.getString(R.string.please_check_the_terms_and_conditions))
        }else{
            if (sharedStorage!!.isLandlord()){
                addMerchantBody()
            }else{
                addCustomerBody()
            }
        }
    }

}
