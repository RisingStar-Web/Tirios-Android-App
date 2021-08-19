package com.ai.tirios.ui.my_profile

import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.BodyUpdateMyProfile
import com.ai.tirios.dataclasses.ResponseUpdateProfile
import com.ai.tirios.ui.setting.SettingsActivity
import com.ai.tirios.utils.Constants.SharedPref.Companion.BASE_URL
import com.ai.tirios.utils.Constants.SharedPref.Companion.UPDATE_USER_INFO_URL
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener

/**
 * Created by Ashish Kadam on 27,May,2021
 */
class MyProfileViewModel internal constructor(datamanager: DataManager) :
    BaseViewModel<MyProfileMedium>(datamanager) {

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun onChnagePasswordClick() = medium.onChnagePasswordClick()

    fun onSaveChangesClick() = medium.onSaveChangesClick()

    fun UpdateProfile(bodyUpdateMyProfile: BodyUpdateMyProfile) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseUpdateProfile> {
            override fun onSuccessfulApi(response: ResponseUpdateProfile) {
                medium.dismissProgressbar()
                if (response.success) {

                    var sharedStorage = SharedStorage(medium.myActivity!!)
                    sharedStorage.setemail(response.data.email.toString())
                    medium.navigateTo(SettingsActivity::class.java)
                } else
                    medium.showToast(response.errors.get(0).description)
                medium.dismissProgressbar()

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

        }, dataManager.updateProfile(BASE_URL + UPDATE_USER_INFO_URL, bodyUpdateMyProfile))

    }


}