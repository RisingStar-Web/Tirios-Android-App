package com.ai.tirios.ui.forgot_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ai.tirios.R
import com.ai.tirios.dataclasses.BodyChangePassword
import com.ai.tirios.dataclasses.ResponseOtp
import com.ai.tirios.ui.landlord_login.LandLordLoginActivity
import com.ai.tirios.utils.Constants.SharedPref.Companion.IDENTITY_BASE_URL
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */

class ForgotPasswordViewModel internal constructor(dataManager: DataManager):
    BaseViewModel<ForgotPasswordMedium>(dataManager){

    fun onUpdateClick() = medium.onUpdateClick()

    fun changePassword(context: Context, bodyChangePassword: BodyChangePassword) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseOtp> {
            override fun onSuccessfulApi(response: ResponseOtp) {
                if (!response.success)
                    medium.showToast("Could not find user "+bodyChangePassword.mobile)
                else {
                    medium.showToast(medium.myActivity!!.resources.getString(R.string.password_is_reset_successfully_please_login_again))
                    medium.navigateTo(
                        LandLordLoginActivity::class.java, false, Bundle(),
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
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

        }, dataManager.changePassword(IDENTITY_BASE_URL+"api/auth/resetPassword",bodyChangePassword))
    }
}