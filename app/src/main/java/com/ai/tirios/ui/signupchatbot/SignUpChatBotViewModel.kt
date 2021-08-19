package com.ai.tirios.ui.signupchatbot

import android.content.Context
import android.os.Bundle
import com.ai.tirios.dataclasses.ResponseOtp
import com.ai.tirios.ui.register.RegisterActivity
import com.ai.tirios.ui.send_otp.SendOtpActivity
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

class SignUpChatBotViewModel internal constructor(dataManager: DataManager): BaseViewModel<SignUpChatBotMedium>(dataManager){

    fun onAttachFile() = medium.onAttachFile()

    fun onSendMsg() = medium.onSendMsg()

    fun generateOtp(con:Context, countryCode:String, mobile:String, url:String){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseOtp> {
            override fun onSuccessfulApi(response: ResponseOtp) {
                var bundle = Bundle()
                bundle.putString("CODE", countryCode)
                bundle.putString("MOBILE", mobile)
                if (medium.myActivity!!.intent.hasExtra("from")){
                    bundle.putString("from", medium.myActivity!!.intent.getStringExtra("from"))
                }
                if (response.success) {
                    medium.navigateTo(SendOtpActivity::class.java, false, bundle)
                }else {
                    if (response.errors.get(0).title.equals("Verified", true)){
                        medium.navigateTo(RegisterActivity::class.java, false, bundle)
                    }else if(response.errors.get(0).title.equals("DuplicateMobileNo", true)){
                        medium.showAlert(response.errors.get(0).description)
                    }else if(response.errors.get(0).title.equals("Exists", true)){
                        medium.navigateTo(SendOtpActivity::class.java, false, bundle)
                    }else
                        medium.showToast(response.errors.get(0).description)
                }
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

        }, dataManager.resetPasswordgenerateOtp(url, countryCode+mobile))
    }

    fun resetPasswordOtp(countryCode:String, mobile:String, url:String){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseOtp> {
            override fun onSuccessfulApi(response: ResponseOtp) {
                var bundle = Bundle()
                bundle.putString("CODE", countryCode)
                bundle.putString("MOBILE", mobile)
                if (medium.myActivity!!.intent.hasExtra("from")){
                    bundle.putString("from", medium.myActivity!!.intent.getStringExtra("from"))
                }
                if (response.success) {
                    medium.navigateTo(SendOtpActivity::class.java, false, bundle)
                }else {
                    if (response.errors.get(0).title.equals("Verified", true)){
                        medium.navigateTo(RegisterActivity::class.java, false, bundle)
                    }else if(response.errors.get(0).title.equals("DuplicateMobileNo", true)){
                        medium.showAlert(response.errors.get(0).description)
                    }else if(response.errors.get(0).title.equals("Exists", true)){
                        medium.navigateTo(SendOtpActivity::class.java, false, bundle)
                    }else
                        medium.showToast(response.errors.get(0).description)
                }
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

        }, dataManager.resetPasswordgenerateOtp(url,countryCode+mobile))
    }
}