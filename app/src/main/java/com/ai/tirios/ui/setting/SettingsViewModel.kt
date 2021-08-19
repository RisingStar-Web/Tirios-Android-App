package com.ai.tirios.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.ai.tirios.BuildConfig
import com.ai.tirios.R
import com.ai.tirios.dataclasses.*
import com.ai.tirios.ui.setting.tnc.TermsAndPolicyActivity
import com.ai.tirios.data.DataManager
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.dataclasses.LandLordProperty
import com.ai.tirios.dataclasses.ResponseBankAccounts
import com.ai.tirios.utils.Constants


class SettingsViewModel internal constructor(dataManager: DataManager) :
    BaseViewModel<SettingsMedium>(dataManager) {
    var profileImageUrl = ObservableField<String>()
    val appVer = "App Version ${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
    var bankAccounts: MutableLiveData<ResponseBankAccounts> = MutableLiveData()
    var imageBitmapBase64Download = ObservableField<String>()

    var imageBitmapBase64Upload: MutableLiveData<String> = MutableLiveData()
    var downloadedImage: MutableLiveData<String> = MutableLiveData()

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun dispatchTakePictureIntent() = medium.imagePickerDialog()

    fun signOut() = medium.logoutUser()

    fun getSettingsList(arrayOf: MutableList<String>): ArrayList<Settings> {
        val arrayList = ArrayList<Settings>()
        for (i in 0 until arrayOf.size) {
            if (arrayOf.get(i).equals("My Profile")) {
                arrayList.add(Settings(R.drawable.ic_myprofile, arrayOf.get(i)))
            } else if (arrayOf.get(i).equals("Bank Accounts")) {
                arrayList.add(Settings(R.drawable.ic_mycarddetails, arrayOf.get(i)))
            } else if (arrayOf.get(i).equals("Share App")) {
                arrayList.add(Settings(R.drawable.ic_shareapp, arrayOf.get(i)))
            } else if (arrayOf.get(i).equals("Contact Us")) {
                arrayList.add(Settings(R.drawable.ic_contact, arrayOf.get(i)))
            } else if (arrayOf.get(i).equals("Sign Out")) {
                arrayList.add(Settings(R.drawable.ic_signout, arrayOf.get(i)))
            } else if (arrayOf.get(i).equals("Add Credit Cards")) {
                arrayList.add(Settings(R.drawable.ic_mycarddetails, arrayOf.get(i)))
            }
        }
        return arrayList
    }

    fun viewContent(fileName: String, title: String) {
        val bundle = Bundle()
        bundle.putString("FILE_PATH", fileName)
        bundle.putString("FILE_TITLE", title)
        medium.navigateTo(TermsAndPolicyActivity::class.java, false, bundle)
    }

    fun shareTirios(msg: String) {
        try {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            medium.myActivity?.startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            Log.d("shareException", e.localizedMessage)
        }
    }

    fun getBankAccounts(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseBankAccounts> {
            override fun onSuccessfulApi(response: ResponseBankAccounts) {
                medium.dismissProgressbar()
                bankAccounts.value = response
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

        }, dataManager.getBankAccounts(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Account/$id"))
    }

    fun uploadImage(bodyUploadFile: BodyUploadFile) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseUploadFile> {
            override fun onSuccessfulApi(response: ResponseUploadFile) {
                if (response.status == 200) {
                    imageBitmapBase64Download.set(imageBitmapBase64Upload.value)
                }
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

        }, dataManager.uploadFile(Constants.SharedPref.IDENTITY_BASE_URL +"api/auth/uploadFile", bodyUploadFile))
    }

    fun downloadImage(bodyDownloadImage: BodyDownloadImage) {
        dataManager.apiCall(object : IApiListener<ResponseDownloadPic> {
            override fun onSuccessfulApi(response: ResponseDownloadPic) {
                downloadedImage.value = response[0].documentURL
            }

            override fun onFailureCodeApi(response: String) {
            }

            override fun onFailureApi(e: CustomException) {
            }

            override fun unAuth() {
            }

        }, dataManager.downloadFile(Constants.SharedPref.IDENTITY_BASE_URL +"api/auth/download", bodyDownloadImage))
    }

}