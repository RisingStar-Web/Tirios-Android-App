package com.ai.tirios.ui.send_otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivitySendOtpBinding
import com.ai.tirios.dataclasses.BodyOtp
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.utils.Constants.SharedPref.Companion.IDENTITY_BASE_URL
import kotlinx.android.synthetic.main.activity_send_otp.*
import javax.inject.Inject

class SendOtpActivity : BaseActivity<ActivitySendOtpBinding, SendOtpViewModel>(),
    SendOtpMedium {
    var binding: ActivitySendOtpBinding? = null
    private var timer: CountDownTimer? = null
    var isTimerRunning = false
    var mobileNo: String? = ""
    var countryCode: String? = ""
    var fName: String? = ""
    var lName: String? = ""
    var email: String? = ""
    var tenantId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.sendOtp = viewModel
        getData()
        runTimer()
    }

    private fun getData() {
        mobileNo = intent.getStringExtra("MOBILE")
        countryCode = intent.getStringExtra("CODE")
        if(intent.hasExtra("TENANT_ID")){
            fName = intent.getStringExtra("FIRST_NAME")
            lName = intent.getStringExtra("LAST_NAME")
            email = intent.getStringExtra("EMAIL")
            tenantId = intent.getStringExtra("TENANT_ID")
        }

        println("========>" + countryCode)
    }

    private fun runTimer() {
        isTimerRunning = true
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tv_timer.text = (millisUntilFinished / 1000).toString() + " sec"
            }

            override fun onFinish() {
                tv_timer.text = resources.getString(R.string.otp_exp)
                isTimerRunning = false
            }
        }.start()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_send_otp
    override lateinit var viewModel: SendOtpViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onSendOtp() {
        if (validateDataOtp())
            callServiceForSendingOtp()
    }

    private fun validateDataOtp(): Boolean {
        return when {
            et_otp.otp?.isEmpty()!! -> {
                showToast(resources.getString(R.string.please_enter_the_OTP_for_verification))
                et_otp.showError()
                false
            }
            et_otp.otp?.length != 4 -> {
                showToast(resources.getString(R.string.OTP_number_is_invalid))
                et_otp.showError()
                false
            }
            else -> true
        }
    }

    private fun callServiceForSendingOtp() {
        viewModel.validateOtp(this, countryCode.toString(), mobileNo.toString(), fName!!, lName!!, email!!, tenantId!!,
            IDENTITY_BASE_URL+"api/auth/otpValidate", BodyOtp(countryCode+mobileNo, et_otp.otp.toString()))
    }

    override fun onResendOtp() {
        if (!isTimerRunning) {
            resendOtp(countryCode+mobileNo)
            runTimer()
        } else {
            showToast(resources.getString(R.string.resend_otp_will_be_available_only_after_your))
        }
    }

    private fun resendOtp(mobileNumber: String) {
        viewModel.resendOtp(this, IDENTITY_BASE_URL+"api/auth/resendOTP", mobileNumber)
    }
}
