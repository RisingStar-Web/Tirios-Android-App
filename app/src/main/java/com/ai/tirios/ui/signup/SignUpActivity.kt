package com.ai.tirios.ui.signup

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.BuildConfig
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivitySignUpBinding
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.ai.tirios.utils.Constants.SharedPref.Companion.IDENTITY_BASE_URL
import com.google.firebase.ktx.Firebase
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.utils.Utilities
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(),
    SignUpMedium {
    var binding: ActivitySignUpBinding? = null
    var countryCode = "+1"
    var activityFrom = ""
    var mobileNo: String? = ""
    var fName: String? = ""
    var lName: String? = ""
    var email: String? = ""
    var tenantId: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.signUp = viewModel
        getData()
        binding!!.ccp.setOnCountryChangeListener { countryCode = "+"+binding!!.ccp.selectedCountryCode.toString() }
        binding!!.ccp.isClickable = !Utilities.isProduction()
    }

    private fun getData() {
        activityFrom = intent.getStringExtra("from").toString()
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    var fullMobileNumber = deepLink?.getQueryParameter("mobile")?.trim()
                    var fullLength = fullMobileNumber?.length
                    fName = deepLink?.getQueryParameter("firstName")
                    lName = deepLink?.getQueryParameter("lastName")
                    email = deepLink?.getQueryParameter("email")
                    tenantId = deepLink?.getQueryParameter("tenantID")
                    mobileNo = fullMobileNumber?.takeLast(10)?.trim()
                    et_mobile.setText(mobileNo)
                    var codelength = fullLength!! - 10
                    countryCode = fullMobileNumber?.substring(0, codelength).toString()
                    ccp.isEnabled = false
                    ccp.isClickable = false
                    println("====="+countryCode)
                    if (countryCode.equals("+1", true) || countryCode.toInt() == 1) {
                        ccp.setDefaultCountryUsingNameCode("US")
                        countryCode = "+1"
                    }else
                        ccp.setCountryForPhoneCode(countryCode.toInt())
                    et_mobile.isEnabled = false
//                    println("====="+deepLink?.getQueryParameter("mobile"))
//                    println("====="+deepLink?.getQueryParameter("lastName"))
//                    println("====="+deepLink?.getQueryParameter("tenantID"))
                    println("====="+countryCode)

                }
                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

                // ...
            }
            .addOnFailureListener(this) { e -> Log.w("TAG", "getDynamicLink:onFailure", e) }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_sign_up
    override lateinit var viewModel: SignUpViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    private fun validateDataOtp(): Boolean {
        return when {
            TextUtils.isEmpty(et_mobile.text.toString()) -> {
                if (intent.hasExtra("from"))
                    showToast(resources.getString(R.string.please_enter_the_registered_mobile_number))
                else
                    showToast(resources.getString(R.string.please_enter_mobile_number))
                false
            }
            !utilities?.isValidMobile(et_mobile.text.toString().replace("-",""))!! -> {
                showToast(resources.getString(R.string.please_enter_a_valid_ten_digit_mobile_number))
                false
            }
            else -> true
        }
    }

    private fun callServiceForSendingOtp() {
        if (intent.hasExtra("from")){
            viewModel.resetPasswordOtp(
                countryCode, Utilities.convertValidPhoneNumber(et_mobile.text.toString()),
                IDENTITY_BASE_URL+"api/auth/resetPasswordOTP")
        }else{
            viewModel.generateOtp(
                this, countryCode, Utilities.convertValidPhoneNumber(et_mobile.text.toString()),
                fName!!, lName!!,email!!, tenantId!!,IDENTITY_BASE_URL+"api/auth/otpGenerate")
        }
    }

    override fun onGenerateOtp() {
        if (validateDataOtp())
            callServiceForSendingOtp()
    }

    override fun onStart() {
        super.onStart()
        setTextWatcherSsnNumber()
    }
    private fun setTextWatcherSsnNumber() {
        binding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding?.etMobile?.setOnKeyListener { it, keyCode, event ->
                    if (keyCode != KeyEvent.KEYCODE_DEL) {
                        val len: Int = binding!!.etMobile.text!!.length
                        if (len == 3) {
                            binding?.etMobile?.setText(binding?.etMobile?.text.toString() + "-")
                            binding?.etMobile?.text?.length?.let { it1 ->
                                binding?.etMobile?.setSelection(
                                    it1
                                )
                            }
                        }
                        if (len == 7) {
                            binding?.etMobile?.setText(binding?.etMobile?.text.toString() + "-")
                            binding?.etMobile?.text?.let { it1 -> binding?.etMobile?.setSelection(it1.length) }
                        }
                    }
                    false
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

}
