package com.ai.tirios.ui.send_otp

import android.content.Context
import android.os.Bundle
import com.ai.tirios.dataclasses.BodyOtp
import com.ai.tirios.dataclasses.ResponseOtp
import com.ai.tirios.ui.forgot_password.ForgotPasswordActivity
import com.ai.tirios.ui.register.RegisterActivity
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

class SendOtpViewModel internal constructor(dataManager: DataManager) :
    BaseViewModel<SendOtpMedium>(dataManager) {

    fun onSendOtp() = medium.onSendOtp()
    fun onResendOtp() = medium.onResendOtp()

    fun validateOtp(con: Context, countryCode: String, mobile: String, firstName: String, lastName: String,
                    email: String, tenantId: String, url: String, body: BodyOtp) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseOtp> {
            override fun onSuccessfulApi(response: ResponseOtp) {
                if (response.success) {
                    var bundle = Bundle()
                    bundle.putString("CODE", countryCode)
                    bundle.putString("MOBILE", mobile)
                    if (!tenantId.isNullOrEmpty()){
                        bundle.putString("FIRST_NAME", firstName)
                        bundle.putString("LAST_NAME", lastName)
                        bundle.putString("TENANT_ID", tenantId)
                        bundle.putString("EMAIL", email)
                    }
                    if (medium.myActivity!!.intent.hasExtra("from")){
                        medium.navigateTo(ForgotPasswordActivity::class.java, false, bundle)
                    }else{
                        medium.navigateTo(RegisterActivity::class.java, false, bundle)
                    }
                } else
                    medium.showToast(response.errors.get(0).description)
                medium.dismissProgressbar()
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showToast(response)
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.validateOtp(url, body))
    }

    fun resendOtp(con: Context, url: String, mobileNumber: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseOtp> {
            override fun onSuccessfulApi(response: ResponseOtp) {

                medium.dismissProgressbar()
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showToast(response)
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.resendOtp(url, mobileNumber))
    }
}