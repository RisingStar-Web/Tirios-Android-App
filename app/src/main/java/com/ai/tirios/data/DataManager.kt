package com.ai.tirios.data

import com.ai.tirios.data.api.IApiHelper
import com.ai.tirios.dataclasses.*
import com.ai.tirios.utils.Utilities
import com.ai.tirios.data.api.ApiHelper
import com.ai.tirios.data.api.CustomException
import com.google.gson.JsonObject
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.preference.IPreferencesHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Url
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class DataManager
@Inject
internal constructor(
    private val mIPreferencesHelper: IPreferencesHelper,
    private val mIApiHelper: IApiHelper,
    private val utilities: Utilities
) : IDataManager {

    //  Regarding Api Call
    override fun <T> apiCall(mApiListener: IApiListener<T>, tCall: Call<T>) {
        when {
            utilities.isNetworkAvailable -> tCall.enqueue(object : ApiHelper<T>() {
                override fun onSuccessfulApi(response: T) = mApiListener.onSuccessfulApi(response)

                override fun onFailureCodeApi(response: String) =
                    mApiListener.onFailureCodeApi(response)

                @Throws(JSONException::class)
                override fun onFailureApi(e: CustomException) = mApiListener.onFailureApi(e)

                override fun unAuth() = mApiListener.unAuth()
            })
        }
    }


    //          Regarding Shared Preference
    override fun getAll() = mIPreferencesHelper.getAll()

    override fun getString(key: String) = mIPreferencesHelper.getString(key)

    override fun putString(key: String, value: String) = mIPreferencesHelper.putString(key, value)

    override fun getInt(key: String) = mIPreferencesHelper.getInt(key)

    override fun putInt(key: String, value: Int) = mIPreferencesHelper.putInt(key, value)

    override fun getBoolean(key: String) = mIPreferencesHelper.getBoolean(key)

    override fun putBoolean(key: String, value: Boolean) =
        mIPreferencesHelper.putBoolean(key, value)

    override fun getLong(key: String) = mIPreferencesHelper.getLong(key)

    override fun putLong(key: String, value: Long) = mIPreferencesHelper.putLong(key, value)

    override fun clearPreferences() = mIPreferencesHelper.clearPreferences()

    override fun landlordLogin(
        @Url url: String,
        @Body bodyLogin: BodyLogin
    ): Call<LandLordProfile> =mIApiHelper.landlordLogin(url, bodyLogin)

    override fun changePassword(url: String, bodyChangePassword: BodyChangePassword): Call<ResponseOtp> =
        mIApiHelper.changePassword(url, bodyChangePassword
    )

    override fun generateOtp(url:String): Call<ResponseOtp> =mIApiHelper.generateOtp(url)

    override fun resetPasswordgenerateOtp(url: String, mobilenumber: String): Call<ResponseOtp> =
        mIApiHelper.resetPasswordgenerateOtp(url, mobilenumber)

    override fun validateOtp(url:String, bodyOtp: BodyOtp): Call<ResponseOtp> = mIApiHelper.validateOtp(url, bodyOtp)

    override fun resendOtp(url:String, mobilenumber: String): Call<ResponseOtp> =mIApiHelper.resendOtp(url, mobilenumber)

    override fun getProperties(url: String): Call<LandLordProperty> = mIApiHelper.getProperties(url)

    override fun getPropertyDetails(url: String): Call<Property> = mIApiHelper.getPropertyDetails(url)

    override fun getTenantAgrement(url: String, bodyTenantAgrement: JsonObject): Call<ResponseTenantImageUpload> = mIApiHelper.getTenantAgrement(url, bodyTenantAgrement)

    override fun adTenant(url: String, bodyAddTenant: JsonObject): Call<Tenants> = mIApiHelper.adTenant(url, bodyAddTenant)

    override fun getTenant(url: String): Call<Tenants> = mIApiHelper.getTenant(url)

    override fun editTenant(url: String, bodyEditTenant: JsonObject): Call<Tenants> = mIApiHelper.editTenant(url, bodyEditTenant)

    override fun getProperty(url: String): Call<Property> = mIApiHelper.getProperty(url)

    override fun addProperty(url: String, bodyAddProperty: BodyAddProperty): Call<Property> = mIApiHelper.addProperty(url, bodyAddProperty)

    override fun editProperty(url: String, bodyEditProperty: BodyEditProperty): Call<Property> = mIApiHelper.editProperty(url, bodyEditProperty)

    override fun editISResiding(url: String, bodyIsReciding: BodyIsReciding): Call<Tenants> = mIApiHelper.editISResiding(url, bodyIsReciding)

    override fun propertyImageUpload(
        url: String,
        bodyPropertyImageUpload: BodyPropertyImageUpload
    ): Call<ResponseImageUpload> = mIApiHelper.propertyImageUpload(url, bodyPropertyImageUpload)

    override fun uploadTenantImage(url: String, bodyTenantUpload: BodyTenantUpload): Call<ResponseTenantImageUpload> = mIApiHelper.uploadTenantImage(url, bodyTenantUpload)

    override fun getTenantId(url: String): Call<List<Tenants>> = mIApiHelper.getTenantId(url)

    override fun registerUser(url:String, bodyLogin: BodySignUp): Call<ResponseOtp> =mIApiHelper.registerUser(url, bodyLogin)

    override fun updatePassword(url: String, bodyModifyPassword: BodyModifyPassword): Call<ResponseChangePassword> = mIApiHelper.updatePassword(url, bodyModifyPassword)

    override fun updateProfile(url: String, bodyUpdateMyProfile: BodyUpdateMyProfile): Call<ResponseUpdateProfile> =mIApiHelper.updateProfile(url,bodyUpdateMyProfile)

    override fun uploadFile(url: String, bodyUploadFile: BodyUploadFile): Call<ResponseUploadFile> = mIApiHelper.uploadFile(url, bodyUploadFile)

    override fun downloadFile(url: String, bodyDownloadImage: BodyDownloadImage): Call<ResponseDownloadPic> =  mIApiHelper.downloadFile(url, bodyDownloadImage)

    override fun uploadImageForMaintenanceBot(url:String, body: UploadImageBody): Call<MaintenanceUploadResponse> = mIApiHelper.uploadImageForMaintenanceBot(url, body)

    override fun uploadImageForMaintenanceBotId(url:String, body: UploadImageBody): Call<MaintenanceUploadResponse> = mIApiHelper.uploadImageForMaintenanceBotId(url, body)

    override fun uploadVideoForMaintenanceBot(url: String, MaintenanceRequestId: RequestBody, DocumentLocation: RequestBody,
                                              DocumentName: RequestBody, Document: RequestBody, DocumentType: RequestBody,
                                              DocumentExtension: RequestBody, file: MultipartBody.Part): Call<MaintenanceUploadResponse> = mIApiHelper.uploadVideoForMaintenanceBot(url, MaintenanceRequestId, DocumentLocation, DocumentName, Document, DocumentType, DocumentExtension, file)

    override fun getBankAccounts(url: String): Call<ResponseBankAccounts> = mIApiHelper.getBankAccounts(url)

    override fun addMerchant(url: String, body: BodyAddMerchant): Call<ResponseMerchant> = mIApiHelper.addMerchant(url, body)

    override fun addCustomer(url: String, body: BodyAddCustomer): Call<ResponseAddCustomer> = mIApiHelper.addCustomer(url, body)

    override fun getBankAccountToken(
        url: String,
        body: BodyBankAccountTokenization
    ): Call<BankAccountTokenization> = mIApiHelper.getBankAccountToken(url, body)

    override fun getMaintainance(url: String): Call<List<ResponseMaintainance>> = mIApiHelper.getMaintainance(url)

    override fun getMaintainanceById(url: String): Call<ResponseMaintainance> = mIApiHelper.getMaintainanceById(url)

    override fun getNotificationList(url: String) = mIApiHelper.getNotificationList(url)

    override fun addCreditCard(
        url: String,
        bodyAddCreditCard: BodyAddCreditCard
    ): Call<ResponseCreditCard> = mIApiHelper.addCreditCard(url, bodyAddCreditCard)

    override fun apiUserNotification(
        url: String,
        bodyApiUser: BodyApiUser
    ): Call<ResponseApiUSer> =
        mIApiHelper.apiUserNotification(url, bodyApiUser)

    override fun NotificationRead(
        url: String,
        bodyNotificationRead: BodyNotificationRead
    ): Call<ResponseApiUSer> = mIApiHelper.NotificationRead(url, bodyNotificationRead)

    override fun requestActivation(url: String, jsonObject: JsonObject)
    : Call<Property> = mIApiHelper.requestActivation(url, jsonObject)

    override fun getInvoiceDocument(url: String) : Call<ResponseViewInvoice> = mIApiHelper.getInvoiceDocument(url)

    override fun payWithBank(url: String, bodyPayWithBank: BodyPayWithBank): Call<ResponseBankPay> =
        mIApiHelper.payWithBank(url, bodyPayWithBank)

    override fun payWithCreditCard(
        url: String,
        bodyPayWithBank: BodyPayWithCard
    ): Call<ResponseBankPay> = mIApiHelper.payWithCreditCard(url, bodyPayWithBank)

    override fun updateInvoice(url: String, bodyUpdateInvoice: UpdateInvoice) : Call<ResponseUpdateInvoice> = mIApiHelper.updateInvoice(url, bodyUpdateInvoice)

    override fun updateRent(url: String, bodyUpdateRent: UpdateRent) : Call<ResponseUpdateInvoice> = mIApiHelper.updateRent(url, bodyUpdateRent)

}
