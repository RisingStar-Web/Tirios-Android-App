package com.ai.tirios.ui.register

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.BuildConfig
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivityRegisterBinding
import com.ai.tirios.dataclasses.BodySignUp
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.utils.Utilities
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),
    RegisterMedium {

    var binding: ActivityRegisterBinding? = null
    var mobileNo: String? = ""
    var countryCode: String?  = ""
    var fName: String? = ""
    var lName: String? = ""
    var email: String? = ""
    var tenantId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.register = viewModel
        binding!!.ccp.isClickable = !Utilities.isProduction()
        getData()
    }

    private fun getData() {
        mobileNo = intent.getStringExtra("MOBILE")
        countryCode = intent.getStringExtra("CODE")
        ccp.setCountryForPhoneCode(countryCode?.toInt()!!)
        et_mobile.setText(mobileNo)
        if(intent.hasExtra("TENANT_ID")){
            fName = intent.getStringExtra("FIRST_NAME")
            lName = intent.getStringExtra("LAST_NAME")
            email = intent.getStringExtra("EMAIL")
            tenantId = intent.getStringExtra("TENANT_ID")
            et_firstName.setText(fName)
            et_lastName.setText(lName)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_register

    override lateinit var viewModel: RegisterViewModel
        @Inject internal set

    override val bindingVariable: Int
        get() = BR._all

    override fun onSubmitClick() {
        if (validateDataOtp())
            callServiceForSendingOtp()
    }


    private fun validateDataOtp(): Boolean {
        return when {
            TextUtils.isEmpty(et_firstName.text.toString()) -> {
                showToast(resources.getString(R.string.Please_enter_first_name))
                false
            }
            !utilities?.isNameFormat(et_firstName.text.toString())!! -> {
                showToast(resources.getString(R.string.enter_valid_first_name))
                false
            }
            TextUtils.isEmpty(et_lastName.text.toString()) -> {
                showToast(resources.getString(R.string.please_enter_last_name))
                false
            }
            !utilities?.isNameFormat(et_lastName.text.toString())!! -> {
                showToast(resources.getString(R.string.enter_valid_last_name))
                false
            }
            TextUtils.isEmpty(et_mobile.text.toString()) -> {
                showToast(resources.getString(R.string.please_enter_mobile_number))
                false
            }
            !utilities?.isValidMobile(et_mobile.text.toString())!! -> {
                showToast(resources.getString(R.string.enter_valid_mobile_number))
                false
            }
            TextUtils.isEmpty(et_password.text.toString()) -> {
                showToast(resources.getString(R.string.please_enter_passwrod))
                false
            }
            !utilities?.isValidPassword(et_password.text.toString())!! -> {
                showToast(resources.getString(R.string.password_validation))
                false
            }
            TextUtils.isEmpty(et_cPassword.text.toString()) -> {
                showToast(resources.getString(R.string.passwords_do_not_match))
                false
            }
            !utilities?.isValidPassword(et_cPassword.text.toString())!! -> {
                showToast(resources.getString(R.string.passwords_do_not_match))
                false
            }
            et_password.text.toString() != et_cPassword.text.toString() ->{
                showToast(resources.getString(R.string.passwords_do_not_match))
                false
            }
            else -> true
        }
    }

    private fun callServiceForSendingOtp() {
        viewModel.registerUser(this, "http://ec2-3-229-26-172.compute-1.amazonaws.com/api/auth/signup", tenantId!!, email!!,
        BodySignUp(countryCode+mobileNo, binding!!.etFirstName.text.toString(), binding!!.etLastName.text.toString(),
            binding!!.etPassword.text.toString(), binding!!.etPassword.text.toString(),
            if(tenantId.isNullOrEmpty()) false
            else
                true)
        )
    }
}
