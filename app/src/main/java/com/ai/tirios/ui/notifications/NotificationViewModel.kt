package com.ai.tirios.ui.notifications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.dataclasses.BodyNotificationRead
import com.ai.tirios.dataclasses.ResponseApiUSer
import com.ai.tirios.dataclasses.ResponseNotifications
import com.ai.tirios.utils.Constants

class NotificationViewModel internal constructor(dataManager: DataManager) :
    BaseViewModel<NotificationMedium>(dataManager) {

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    var notification_list : MutableLiveData<List<ResponseNotifications>> = MutableLiveData()
    var notification_array: MutableList<ResponseNotifications> = mutableListOf()


    fun getNotificatonList(userID: String) {
        medium.showProgressbar()
        dataManager.apiCall(
            object : IApiListener<List<ResponseNotifications>> {
                override fun onSuccessfulApi(response: List<ResponseNotifications>) {
                    notification_array.clear()
                    notification_array.addAll(response)
                    notification_list.value = response
                    medium.dismissProgressbar()
                }

                override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showToast(response)
            }

                override fun onFailureApi(e: CustomException) {
                    medium.dismissProgressbar()
                }

                override fun unAuth() {
                    medium.dismissProgressbar()
                }


            },
            dataManager.getNotificationList(Constants.SharedPref.NOTIFICATION_BASE_URL + Constants.SharedPref.NOTIFICATION_LIST_URL + userID)
        )
    }


    fun onNotificationClick(idNotification: BodyNotificationRead) {
        medium.showProgressbar()
        dataManager.apiCall(
            object : IApiListener<ResponseApiUSer> {
                override fun onSuccessfulApi(response: ResponseApiUSer) {
                    //  TODO("Not yet implemented")
                    medium.dismissProgressbar()
                }

                override fun onFailureCodeApi(response: String) {
                    //  TODO("Not yet implemented")
                    medium.dismissProgressbar()
                }

                override fun onFailureApi(e: CustomException) {
                    //  TODO("Not yet implemented")
                    medium.dismissProgressbar()
                }

                override fun unAuth() {
                    //  TODO("Not yet implemented")
                    medium.dismissProgressbar()
                }

            },
            dataManager.NotificationRead(
                Constants.SharedPref.NOTIFICATION_BASE_URL + Constants.SharedPref.NOTIFICATION_LIST_URL,
                idNotification
            )
        )
    }

}

