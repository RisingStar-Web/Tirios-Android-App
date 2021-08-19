package com.ai.tirios.ui.landlord_login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ai.tirios.R
import com.ai.tirios.ui.error_message.ErrorMessageActivity
import com.ai.tirios.ui.main.MainActivity
import com.ai.tirios.ui.signupchatbot.SignUpChatBotActivity
import com.ai.tirios.utils.Constants.SharedPref.Companion.IDENTITY_BASE_URL
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.*
import com.ai.tirios.utils.Constants.SharedPref.Companion.NOTIFICATION_BASE_URL
import com.ai.tirios.utils.Constants.SharedPref.Companion.NOTIFICATION_USER_URL
import com.ai.tirios.utils.Constants.SharedPref.Companion.PROPERTY_BASE_URL

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */

class LandLordViewModel internal constructor(dataManager: DataManager)
    : BaseViewModel<LandLordMedium>(dataManager) {

    fun onForgotPasswordClick() = medium.forgotPassword()

    fun onLoginClick() = medium.login()

    fun onBackPressed() = medium.onBackPressed()

    fun loginApi(context: Context, bodyLogin: BodyLogin) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<LandLordProfile> {
            override fun onSuccessfulApi(response: LandLordProfile) {
                medium.dismissProgressbar()
                if (response.token == null)
                    medium.showAlert("Username does not exist")
                else {
                    var sharedStorage= SharedStorage(medium.myActivity!!)
                    val deviceId = sharedStorage.getDeviceId()
                    sharedStorage.clearStorage()
                    deviceId?.let { sharedStorage.setDeviceId(it) }

                    val bodyApiUser = deviceId?.let {
                        BodyApiUser(
                            response.id,
                            it
                        )
                    }
                    bodyApiUser?.let { userApiCallNotification(it) }

                    if (response.emailConfirmed && response.documentsUploaded){
                        if (response.status == "Verified"){

                            sharedStorage.setid(response.id)
                            sharedStorage.setfirstName(response.firstName)
                            sharedStorage.setlastName(response.lastName)
                            sharedStorage.setusername(response.username)
                            sharedStorage.settoken("Bearer "+response.token)
                            sharedStorage.setrole(response.role)
                            sharedStorage.setLandlord(response.role)
                            if (response.email != null)
                                sharedStorage.setemail(response.email)
                            sharedStorage.setStatus(response.status)
                            sharedStorage.setDocumentsUploaded(response.documentsUploaded)
                            sharedStorage.setEmailConfirmed(response.emailConfirmed)

                            medium.navigateTo(
                                MainActivity::class.java, false, Bundle(),
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        }else if (response.status == "Pending"){
                            var bundle= Bundle()
                            bundle.putString("status", "Pending")
                            medium.navigateTo(ErrorMessageActivity::class.java, false, bundle)
                        }else if (response.status == "Rejected"){
                            var bundle= Bundle()
                            bundle.putString("status", "Rejected")
                            medium.navigateTo(ErrorMessageActivity::class.java, false, bundle)
                        }
                    }else if (!response.emailConfirmed || !response.documentsUploaded){
                        if (sharedStorage.isLandlord()){
                            var bundle = Bundle()
                            bundle.putString("USER_ID",response.id)
                            bundle.putString("FIRST_NAME",response.firstName)
                            bundle.putString("LAST_NAME",response.lastName)
                            bundle.putString("MOBILE", response.username)
                            medium.navigateTo(
                                SignUpChatBotActivity::class.java, false, bundle,
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        }else{
                            getTenantId(response.id)
                        }
                    }else{
                        sharedStorage.setid(response.id)
                        sharedStorage.setfirstName(response.firstName)
                        sharedStorage.setlastName(response.lastName)
                        sharedStorage.setusername(response.username)
                        sharedStorage.settoken("Bearer "+response.token)
                        sharedStorage.setrole(response.role)
                        sharedStorage.setLandlord(response.role)
                        if (response.email != null)
                            sharedStorage.setemail(response.email)
                        sharedStorage.setStatus(response.status)
                        sharedStorage.setDocumentsUploaded(response.documentsUploaded)
                        sharedStorage.setEmailConfirmed(response.emailConfirmed)

                        medium.navigateTo(
                            MainActivity::class.java, false, Bundle(),
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }
                }
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                showAlert("Invalid Username and Password")
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.landlordLogin(IDENTITY_BASE_URL+"api/auth", bodyLogin))
    }

    private fun showAlert(msg : String) {
        val builder = AlertDialog.Builder(medium.myActivity)
        builder.setMessage(msg)
        builder.setTitle(medium.myActivity?.resources?.getString(R.string.alert))
        builder.setNegativeButton(medium.myActivity?.resources?.getString(R.string.btnOk)) { dialog, id ->
            dialog.dismiss()
        }.show()    }

    fun getTenantId(user_id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<List<Tenants>> {
            override fun onSuccessfulApi(response: List<Tenants>) {
                medium.dismissProgressbar()
                var sharedStorage = SharedStorage(medium.myActivity!!)
                var bundle = Bundle()
                bundle.putString("USER_ID", sharedStorage.getid())
                bundle.putString("FIRST_NAME", sharedStorage.getfirstName())
                bundle.putString("LAST_NAME", sharedStorage.getlastName())
                bundle.putString("MOBILE", sharedStorage.getusername())
                if (response.get(0).TenantId != null){
                    sharedStorage.setTenantId(response.get(0).TenantId.toString())
                    bundle.putString("TENANT_ID", response.get(0).TenantId.toString())
                    bundle.putString("EMAIL", response.get(0).Email)
                }
                medium.navigateTo(
                    SignUpChatBotActivity::class.java, false, bundle,
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                )
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

        }, dataManager.getTenantId(PROPERTY_BASE_URL+"tenant/$user_id/getbyuserid"))
    }


    fun userApiCallNotification(bodyApiUser: BodyApiUser){
        dataManager.apiCall(object :IApiListener<ResponseApiUSer>{
            override fun onSuccessfulApi(response: ResponseApiUSer) {
                Log.d("Push Notification Token", "device token sent")
            }

            override fun onFailureCodeApi(response: String) {
                Log.d("Push Notification Token", "device token onFailureCodeApi")

            }

            override fun onFailureApi(e: CustomException) {
                Log.d("Push Notification Token", "device token onFailureApi")

            }

            override fun unAuth() {
                Log.d("Push Notification Token", "device token unAuth")

            }

        },
            dataManager.apiUserNotification(
                NOTIFICATION_BASE_URL + NOTIFICATION_USER_URL,
                bodyApiUser
            )
        )
    }

}
