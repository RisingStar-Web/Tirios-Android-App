package com.ai.tirios.ui.add_tenant_bot

import android.content.Context
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.dataclasses.BodyTenantUpload
import com.ai.tirios.dataclasses.MaintenanceUploadResponse
import com.ai.tirios.dataclasses.ResponseTenantImageUpload
import com.ai.tirios.dataclasses.UploadImageBody
import com.ai.tirios.utils.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class AddTenantBotViewModel internal constructor(dataManager: DataManager): BaseViewModel<AddTenantBotMedium>(dataManager){

    fun onAttachFile() = medium.onAttachFile()

    fun onSendMsg() = medium.onSendMsg()

    fun dispatchTakePictureIntent() = medium.dispatchTakePictureIntent()

    fun dispatchTakeVideoIntent() = medium.dispatchTakeVideoIntent()

    fun uploadTenantImage(tenant_id:String, bodyTenantUpload: BodyTenantUpload) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseTenantImageUpload> {
            override fun onSuccessfulApi(response: ResponseTenantImageUpload) {
                medium.onAddTenantImageUpload(Constants.SharedPref.WATSON_VIDEO, response.DocumentURL)
                medium.dismissProgressbar()
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.uploadTenantImage(Constants.SharedPref.PROPERTY_BASE_URL +"tenant/$tenant_id/upload", bodyTenantUpload))
    }

}