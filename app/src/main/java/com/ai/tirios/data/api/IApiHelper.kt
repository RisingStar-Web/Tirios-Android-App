package com.ai.tirios.data.api

import com.ai.tirios.dataclasses.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IApiHelper {

    @POST
    fun landlordLogin(@Url url: String, @Body bodyLogin: BodyLogin): Call<LandLordProfile>

    @POST
    fun changePassword(@Url url: String, @Body bodyChangePassword: BodyChangePassword): Call<ResponseOtp>

    @POST
    fun registerUser(@Url url:String, @Body bodyLogin: BodySignUp): Call<ResponseOtp>

    @POST
    fun generateOtp(@Url url:String): Call<ResponseOtp>

    @POST
    fun resetPasswordgenerateOtp(@Url url:String, @Query("mobile") mobilenumber: String): Call<ResponseOtp>

    @POST
    fun validateOtp(@Url url:String, @Body body: BodyOtp): Call<ResponseOtp>

    @POST
    fun resendOtp(@Url url:String, @Query("mobile") mobilenumber: String): Call<ResponseOtp>

    @GET
    fun getProperties(@Url url: String): Call<LandLordProperty>

    @GET
    fun getPropertyDetails(@Url url: String): Call<Property>

    @POST
    fun getTenantAgrement(@Url url: String, @Body bodyTenantAgrement: JsonObject): Call<ResponseTenantImageUpload>

    @POST
    fun adTenant(@Url url: String, @Body bodyAddTenant: JsonObject): Call<Tenants>

    @GET
    fun getTenant(@Url url: String): Call<Tenants>

    @PUT
    fun editTenant(@Url url: String, @Body bodyEditTenant: JsonObject): Call<Tenants>

    @GET
    fun getProperty(@Url url: String): Call<Property>

    @POST
    fun addProperty(@Url url: String, @Body bodyAddProperty: BodyAddProperty): Call<Property>

    @PUT
    fun editProperty(@Url url: String, @Body bodyEditProperty: BodyEditProperty): Call<Property>

    @PUT
    fun editISResiding(@Url url: String, @Body bodyIsReciding: BodyIsReciding): Call<Tenants>

    @PUT
    fun propertyImageUpload(@Url url: String, @Body bodyPropertyImageUpload: BodyPropertyImageUpload): Call<ResponseImageUpload>

    @POST
    fun uploadTenantImage(@Url url: String, @Body bodyTenantUpload: BodyTenantUpload): Call<ResponseTenantImageUpload>

    @GET
    fun getTenantId(@Url url: String):Call<List<Tenants>>

    @PUT
    fun updatePassword(@Url url: String, @Body bodyModifyPassword: BodyModifyPassword): Call<ResponseChangePassword>

    @GET
    fun getMaintainance(@Url url: String): Call<List<ResponseMaintainance>>

    @GET
    fun getMaintainanceById(@Url url: String): Call<ResponseMaintainance>


    @POST
    fun uploadImageForMaintenanceBot(@Url url:String, @Body body: UploadImageBody): Call<MaintenanceUploadResponse>

    @PUT
    fun uploadImageForMaintenanceBotId(@Url url:String, @Body body: UploadImageBody): Call<MaintenanceUploadResponse>

   @Multipart
    @PUT
    fun uploadVideoForMaintenanceBot(
        @Url url: String, @Part("MaintenanceRequestId") MaintenanceRequestId: RequestBody,
        @Part("DocumentLocation") DocumentLocation: RequestBody,
        @Part("DocumentName") DocumentName: RequestBody,
        @Part("Document") Document: RequestBody,
        @Part("DocumentType") DocumentType: RequestBody,
        @Part("DocumentExtension") DocumentExtension: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<MaintenanceUploadResponse>

    @GET
    fun getBankAccounts(@Url url:String):Call<ResponseBankAccounts>

    @POST
    fun addMerchant(@Url url: String,@Body body: BodyAddMerchant):Call<ResponseMerchant>

    @POST
    fun addCustomer(@Url url: String,@Body body: BodyAddCustomer):Call<ResponseAddCustomer>

    @POST
    fun getBankAccountToken(@Url url: String,@Body body: BodyBankAccountTokenization):Call<BankAccountTokenization>

    @POST
    fun updateProfile(@Url url: String, @Body bodyUpdateMyProfile: BodyUpdateMyProfile): Call<ResponseUpdateProfile>

    @POST
    fun uploadFile(@Url url: String, @Body bodyUploadFile: BodyUploadFile): Call<ResponseUploadFile>

    @POST
    fun downloadFile(@Url url: String, @Body bodyDownloadImage: BodyDownloadImage): Call<ResponseDownloadPic>

    @GET
    fun getNotificationList(@Url url: String):Call<List<ResponseNotifications>>

    @POST
    fun addCreditCard(@Url url: String, @Body bodyAddCreditCard: BodyAddCreditCard): Call<ResponseCreditCard>
    @POST
    fun apiUserNotification(@Url url: String, @Body bodyApiUser:BodyApiUser): Call<ResponseApiUSer>

    @PUT
    fun NotificationRead(@Url url: String, @Body bodyNotificationRead: BodyNotificationRead): Call<ResponseApiUSer>

    @PUT
    fun requestActivation(@Url url: String, @Body jsonObject: JsonObject): Call<Property>

    @POST
    fun payWithBank(@Url url: String, @Body bodyPayWithBank: BodyPayWithBank): Call<ResponseBankPay>

    @POST
    fun payWithCreditCard(@Url url: String, @Body bodyPayWithBank: BodyPayWithCard): Call<ResponseBankPay>

    @GET
    fun getInvoiceDocument(@Url url: String): Call<ResponseViewInvoice>

    @PUT
    fun updateInvoice(@Url url: String, @Body bodyUpdateInvoice: UpdateInvoice): Call<ResponseUpdateInvoice>

    @PUT
    fun updateRent(@Url url: String, @Body bodyUpdateRent: UpdateRent): Call<ResponseUpdateInvoice>
}
