package com.ai.tirios.ui.landlord_login

import com.ai.tirios.base.Medium

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */
interface LandLordMedium: Medium {
    fun login()
    fun forgotPassword()
    fun onBackPressed()
}