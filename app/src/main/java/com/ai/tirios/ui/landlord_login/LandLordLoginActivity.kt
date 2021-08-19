package com.ai.tirios.ui.landlord_login

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.KeyEvent
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.BuildConfig
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.databinding.ActivityLandLordLoginBinding
import com.ai.tirios.dataclasses.BodyLogin
import com.ai.tirios.ui.signup.SignUpActivity
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.utils.Utilities
import com.bumptech.glide.util.Util
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import javax.inject.Inject

class LandLordLoginActivity : BaseActivity<ActivityLandLordLoginBinding, LandLordViewModel>(),
    LandLordMedium {

    var binding: ActivityLandLordLoginBinding? = null
    var countryCode = "+1"
    var countryCodeName = "US"
    private var sharedStorage: SharedStorage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedStorage = SharedStorage(this)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.landLordLogin = viewModel
        val styleSpan = StyleSpan(Typeface.BOLD)
        val wordtoSpan = SpannableString(getString(R.string.tirios_all_rights_reserved))
        wordtoSpan.setSpan(
            styleSpan,
            getString(R.string.tirios_all_rights_reserved).indexOf(getString(R.string.all_rights_reserved)),
            getString(R.string.tirios_all_rights_reserved).indexOf(getString(R.string.all_rights_reserved)) + getString(
                R.string.all_rights_reserved
            ).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        wordtoSpan.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.white)),
            getString(R.string.tirios_all_rights_reserved).indexOf(getString(R.string.all_rights_reserved)),
            getString(R.string.tirios_all_rights_reserved).indexOf(getString(R.string.all_rights_reserved)) + getString(
                R.string.all_rights_reserved
            ).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding!!.ccp.setOnCountryChangeListener(object : CountryCodePicker.OnCountryChangeListener{
            override fun onCountrySelected(selectedCountry: Country?) {
                countryCode = "+"+binding!!.ccp.selectedCountryCode.toString()
                countryCodeName = binding!!.ccp.selectedCountryNameCode
            }

        })

        //binding!!.etMobile.addTextChangedListener( PhoneNumberFormattingTextWatcher())

        binding!!.tvTitiosRights.setMovementMethod(LinkMovementMethod.getInstance())
        binding!!.tvTitiosRights.setText(wordtoSpan)
        binding!!.ccp.isClickable = !Utilities.isProduction()

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_land_lord_login
    override lateinit var viewModel: LandLordViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun login() {
        if (binding!!.etMobile.text.isNullOrEmpty()) {
            showToast(resources.getString(R.string.please_enter_mobile_number))
        } else if (binding!!.etPassword.text!!.isNullOrEmpty()) {
            showToast(resources.getString(R.string.please_enter_passwrod))
        } else {
            sharedStorage?.setCountryFlag(countryCodeName);
            viewModel.loginApi(
                this,
                BodyLogin(countryCode+Utilities.convertValidPhoneNumber(binding!!.etMobile.text.toString()), binding!!.etPassword.text.toString())
            )
        }
    }

    override fun forgotPassword() {
        var bundle = Bundle()
        bundle.putString("from", "ForgotPassword")
        navigateTo(SignUpActivity::class.java, false, bundle)
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
