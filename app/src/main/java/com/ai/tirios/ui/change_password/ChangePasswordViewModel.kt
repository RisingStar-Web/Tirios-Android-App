package com.ai.tirios.ui.change_password

import android.content.Intent
import android.os.Bundle
import com.ai.tirios.R
import com.ai.tirios.dataclasses.BodyModifyPassword
import com.ai.tirios.dataclasses.ResponseChangePassword
import com.ai.tirios.ui.landlord_login.LandLordLoginActivity
import com.ai.tirios.utils.Constants.SharedPref.Companion.BASE_URL
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

class ChangePasswordViewModel internal constructor(dataManager: DataManager): BaseViewModel<ChangePasswordMedium>(dataManager) {

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun changePassword() = medium.changePassword()

    fun changePassword(bodyModifyPassword: BodyModifyPassword) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseChangePassword> {
            override fun onSuccessfulApi(response: ResponseChangePassword) {
                medium.dismissProgressbar()
                if (!response.success)
                    medium.showToast("Could not find user " + bodyModifyPassword.userName)
                else {
                    medium.showToast(medium.myActivity!!.resources.getString(R.string.password_changed_successfully))
                    medium.navigateTo(
                        LandLordLoginActivity::class.java, false, Bundle(),
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                }
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showToast("Error: $response")
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.showToast("Exception: ${e.exception}")
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.updatePassword(BASE_URL + "api/auth/changePassword", bodyModifyPassword))
    }
}