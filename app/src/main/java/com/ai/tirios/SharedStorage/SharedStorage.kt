package com.ai.tirios.SharedStorage

import android.content.Context
import android.content.SharedPreferences
import com.ai.tirios.utils.Constants.SharedPref.Companion.ACCESS_TOKEN
import com.ai.tirios.utils.Constants.SharedPref.Companion.COUNTRY_FLAG
import com.ai.tirios.utils.Constants.SharedPref.Companion.DEVICE_TOKEN
import com.ai.tirios.utils.Constants.SharedPref.Companion.DOCUMENTS_UPLOADED
import com.ai.tirios.utils.Constants.SharedPref.Companion.EMAIL
import com.ai.tirios.utils.Constants.SharedPref.Companion.EMAIL_CONFIRMED
import com.ai.tirios.utils.Constants.SharedPref.Companion.FIRST_NAME
import com.ai.tirios.utils.Constants.SharedPref.Companion.ID
import com.ai.tirios.utils.Constants.SharedPref.Companion.ISLANDLORD
import com.ai.tirios.utils.Constants.SharedPref.Companion.LAST_NAME
import com.ai.tirios.utils.Constants.SharedPref.Companion.ROLE
import com.ai.tirios.utils.Constants.SharedPref.Companion.SHARED_PREF
import com.ai.tirios.utils.Constants.SharedPref.Companion.STATUS
import com.ai.tirios.utils.Constants.SharedPref.Companion.TENANT_ID
import com.ai.tirios.utils.Constants.SharedPref.Companion.USER_NAME

class SharedStorage constructor(context: Context) {

    var sharedPreferences: SharedPreferences

    companion object {
        private var storage: SharedStorage? = null
    }

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    fun getInstance(context: Context): SharedStorage? {
        if (storage == null) storage = SharedStorage(context)
        return storage
    }

    fun clearStorage() {
        sharedPreferences.edit().clear().commit()
    }

    fun setid(token: String){
        sharedPreferences.edit().putString(ID, token).commit()
    }

    fun getid(): String?{
        return sharedPreferences.getString(ID, "")
    }

    fun setfirstName(token: String){
        sharedPreferences.edit().putString(FIRST_NAME, token).commit()
    }

    fun getfirstName(): String?{
        return sharedPreferences.getString(FIRST_NAME, "")
    }

    fun setlastName(token: String){
        sharedPreferences.edit().putString(LAST_NAME, token).commit()
    }

    fun getlastName(): String?{
        return sharedPreferences.getString(LAST_NAME, "")
    }

    fun setusername(token: String){
        sharedPreferences.edit().putString(USER_NAME, token).commit()
    }

    fun getusername(): String?{
        return sharedPreferences.getString(USER_NAME, "")
    }

    fun settoken(token: String){
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).commit()
    }

    fun gettoken(): String?{
        return sharedPreferences.getString(ACCESS_TOKEN, "")
    }

    fun setrole(token: String){
        sharedPreferences.edit().putString(ROLE, token).commit()
    }

    fun getrole(): String?{
        return sharedPreferences.getString(ROLE, "")
    }

    fun setLandlord(role: String){
        sharedPreferences.edit().putBoolean(ISLANDLORD, role.equals("Landlord")).commit()
    }

    fun isLandlord(): Boolean{
        return sharedPreferences.getBoolean(ISLANDLORD, false)
    }

    fun setemail(token: String){
        sharedPreferences.edit().putString(EMAIL, token).commit()
    }

    fun getemail(): String?{
        return sharedPreferences.getString(EMAIL, "")
    }

    fun setStatus(status: String){
        sharedPreferences.edit().putString(STATUS, status).commit()
    }

    fun getStatus(): String?{
        return sharedPreferences.getString(STATUS, "")
    }

    fun setEmailConfirmed(emailConfirmed: Boolean){
        sharedPreferences.edit().putBoolean(EMAIL_CONFIRMED, emailConfirmed).commit()
    }

    fun isEmailConfirmed(): Boolean{
        return sharedPreferences.getBoolean(EMAIL_CONFIRMED, false)
    }

    fun setDocumentsUploaded(documentsUploaded: Boolean){
        sharedPreferences.edit().putBoolean(DOCUMENTS_UPLOADED, documentsUploaded).commit()
    }

    fun isDocumentsUploaded(): Boolean{
        return sharedPreferences.getBoolean(DOCUMENTS_UPLOADED, false)
    }

    fun setTenantId(tenant_id: String){
        sharedPreferences.edit().putString(TENANT_ID, tenant_id).commit()
    }

    fun getTenantId(): String?{
        return sharedPreferences.getString(TENANT_ID, "")
    }

    fun getDeviceId(): String?{
        return sharedPreferences.getString(DEVICE_TOKEN, "")
    }

    fun setDeviceId(device_id: String){
        sharedPreferences.edit().putString(DEVICE_TOKEN, device_id).commit()
    }

    fun getCountryFlag(): String?{
        return sharedPreferences.getString(COUNTRY_FLAG, "")
    }

    fun setCountryFlag(device_id: String){
        sharedPreferences.edit().putString(COUNTRY_FLAG, device_id).commit()
    }
}
