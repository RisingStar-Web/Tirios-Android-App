package com.ai.tirios.ui.maintenance_bot

import android.content.Context
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.MaintenanceUploadResponse
import com.ai.tirios.dataclasses.UploadImageBody
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class MaintenanceBotViewModel internal constructor(dataManager: DataManager): BaseViewModel<MaintenanceBotMedium>(dataManager){

    fun onAttachFile() = medium.onAttachFile()

    fun onSendMsg() = medium.onSendMsg()

    fun dispatchTakePictureIntent() = medium.dispatchTakePictureIntent()

    fun dispatchTakeVideoIntent() = medium.dispatchTakeVideoIntent()

    fun uploadImage(con:Context, url:String, body:UploadImageBody){
//        println("========>"+body.toString())
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<MaintenanceUploadResponse> {
            override fun onSuccessfulApi(response: MaintenanceUploadResponse) {
                medium.onMaintenanceIdUpdate(response.Id, response.Documents.get(0).DocumentLocation+"#"+response.Id)
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

        }, dataManager.uploadImageForMaintenanceBot(url, body))
    }

    fun uploadImageWithId(con:Context, url:String, body:UploadImageBody){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<MaintenanceUploadResponse> {
            override fun onSuccessfulApi(response: MaintenanceUploadResponse) {
                medium.onMaintenanceIdUpdate(response.Id, response.Documents.get(0).DocumentLocation+"#"+response.Id)
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

        }, dataManager.uploadImageForMaintenanceBotId(url, body))
    }

    fun uploadVideo(con:Context, url:String, maintenanceRequestId: Int, docLocation:String, docName:String,
                    docType:Int, docExtension:String){
        val mId: RequestBody = maintenanceRequestId.toString().toRequestBody(MultipartBody.FORM)
        val dLocation: RequestBody = docLocation.toRequestBody(MultipartBody.FORM)
        val dName: RequestBody = docName.toRequestBody(MultipartBody.FORM)
        val doc: RequestBody = docLocation.toRequestBody(MultipartBody.FORM)
        val dType: RequestBody = docType.toString().toRequestBody(MultipartBody.FORM)
        val dExtension: RequestBody = docExtension.toString().toRequestBody(MultipartBody.FORM)
        var filePath = File(docLocation)
        val reqFile = filePath.asRequestBody("*/*".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData("file", filePath.name, reqFile)

        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<MaintenanceUploadResponse> {
            override fun onSuccessfulApi(response: MaintenanceUploadResponse) {
                medium.onMaintenanceUpdate(Constants.SharedPref.WATSON_VIDEO)
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

        }, dataManager.uploadVideoForMaintenanceBot(url, mId, dLocation, dName, doc, dType, dExtension, file))
    }

}