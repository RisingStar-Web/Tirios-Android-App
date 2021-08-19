package com.ai.tirios.ui.maintenance_details

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.ResponseMaintainance
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
class MaintenanceDetailsViewModel internal constructor(dataManager: DataManager): BaseViewModel<MaintenanceDetailsMedium>(dataManager) {

    var maintainance: MutableLiveData<ResponseMaintainance> = MutableLiveData()

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun getMaintainance(maintenance_request_id: Int) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseMaintainance> {
            override fun onSuccessfulApi(response: ResponseMaintainance) {
                maintainance.value = response
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

        }, dataManager.getMaintainanceById(Constants.SharedPref.PROPERTY_BASE_URL +"maintenance/$maintenance_request_id"))
    }

}
