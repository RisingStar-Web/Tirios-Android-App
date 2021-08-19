package com.ai.tirios.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ai.tirios.utils.Utilities

interface Medium {

    val myActivity: FragmentActivity?

    val utilities: Utilities?

    fun getStringRes(resId: Int): String?

    fun showToast(message: String)

    fun showErrorToast(message: String)

    fun showToast(resId: Int)

    fun showAlert(message: String)

    fun showAlert(resId: Int)

    fun showSnackBar(view: View, message: String)

    fun showSnackBar(message: String)

    fun showProgressbar()

    fun dismissProgressbar()

    fun showNetworkMessage()

    fun logoutUser()

    fun navigateTo(c: Class<*>, isFinishActivity: Boolean = false, bundle: Bundle = Bundle.EMPTY, flag: Int? = null)

    fun finishScreen()

    fun changeFragment(frag: Fragment,tagFragmentName: String, @IdRes id: Int, bundle: Bundle = Bundle.EMPTY)

    fun commonDialog(layoutDialog: Int, isCancelable: Boolean = false): Dialog

    fun dispatchTakePictureIntent()

    fun galleryPictureIntent()

    fun imagePickerDialog()

    fun dispatchTakeVideoIntent()

    fun videoPickerDialog()

    fun galleryVideoIntent()

    fun sesionExpired()

}