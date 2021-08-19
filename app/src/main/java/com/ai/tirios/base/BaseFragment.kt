package com.ai.tirios.base

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ai.tirios.R
import com.ai.tirios.ui.landlord_login.LandLordLoginActivity
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.Constants.SharedPref.Companion.REQUEST_IMAGE_CAPTURE
import com.ai.tirios.utils.Utilities
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel<*>> : Fragment(), Medium, View.OnClickListener {

    var baseActivity: BaseActivity<*, *>? = null
        private set

    private var mRootView: View? = null

    var dataBinding: DB? = null
        private set

    private var mViewModel: VM? = null

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: VM

    override val myActivity: FragmentActivity?
        get() = if (baseActivity != null) baseActivity!!.myActivity else null

    override val utilities: Utilities?
        get() = if (baseActivity != null) baseActivity!!.utilities else null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is BaseActivity<*, *> -> {
                val activity = context as BaseActivity<*, *>?
                this.baseActivity = activity
                activity!!.onFragmentAttached()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = dataBinding!!.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding!!.setVariable(bindingVariable, mViewModel)
        dataBinding!!.executePendingBindings()
    }

    override fun getStringRes(resId: Int) =
        if (baseActivity != null) baseActivity!!.getStringRes(resId) else null

    override fun showToast(message: String) {
        baseActivity?.showToast(message)
    }

    override fun showErrorToast(message: String) {
        baseActivity?.showErrorToast(message)
    }

    override fun showToast(resId: Int) {
        baseActivity?.showToast(resId)
    }

    override fun showAlert(message: String) {
        baseActivity?.showAlert(message)
    }

    override fun showAlert(resId: Int) {
        baseActivity?.showAlert(resId)
    }


    override fun showSnackBar(message: String) {
        baseActivity?.showSnackBar(message)
    }

    override fun showSnackBar(view: View, message: String) {
        baseActivity?.showSnackBar(view, message)
    }

    override fun showProgressbar() {
        baseActivity?.showProgressbar()
    }

    override fun dismissProgressbar() {
        baseActivity?.dismissProgressbar()
    }

    override fun showNetworkMessage() {
        baseActivity?.showNetworkMessage()
    }

    override fun navigateTo(c: Class<*>, isFinishActivity: Boolean, bundle: Bundle, flag: Int?) {
        baseActivity?.navigateTo(c, isFinishActivity, bundle)
    }

    override fun logoutUser() = baseActivity?.logoutUser() ?: showErrorToast("Something wrong")

    override fun changeFragment(
        frag: Fragment,
        tagFragmentName: String,
        @IdRes id: Int,
        bundle: Bundle
    ) {
        frag.arguments = bundle
        baseActivity?.supportFragmentManager!!
            .beginTransaction()
            /*.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )*/
            .replace(id, frag, tagFragmentName)
            .addToBackStack(this.javaClass.simpleName)
            .commit()
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    override fun finishScreen() = baseActivity!!.finishScreen()

    override fun commonDialog(layoutDialog: Int, isCancelable: Boolean) =
        baseActivity!!.commonDialog(layoutDialog, isCancelable)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) =
        baseActivity!!.onRequestPermissionsResult(requestCode, permissions, grantResults)

    override fun dispatchTakePictureIntent(){
        if (hasPermission(android.Manifest.permission.CAMERA)){
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }else{
            requestPermissionsSafely(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.SharedPref.CAMERA_REQUEST_CODE
            )
        }
    }

    override fun dispatchTakeVideoIntent(){
        if (hasPermission(android.Manifest.permission.CAMERA)){
            val takePictureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,
                    Constants.SharedPref.REQUEST_VIDEO_CAPTURE
                )
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }else{
            requestPermissionsSafely(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.SharedPref.CAMERA_REQUEST_CODE
            )
        }
    }

    override fun galleryPictureIntent() {
        if (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            try {
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                    Constants.SharedPref.GALLERY_IMAGE_CAPTURE
                )
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }else{
            requestPermissionsSafely(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.SharedPref.CAMERA_REQUEST_CODE
            )
        }
    }

    override fun galleryVideoIntent() {
        if (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            try {
                startActivityForResult(Intent.createChooser(intent, "Select Video"),
                    Constants.SharedPref.GALLERY_VIDEO_CAPTURE
                )
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }else{
            requestPermissionsSafely(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE),
                Constants.SharedPref.CAMERA_REQUEST_CODE
            )
        }
    }

    override fun imagePickerDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.choose))
        builder.setPositiveButton(resources.getString(R.string.take_a_photo)) { dialog, id ->
            dispatchTakePictureIntent()
        }
        builder.setNegativeButton(resources.getString(R.string.choose_from_gallery)){ dialog, id ->
            galleryPictureIntent()
        }.show()
    }

    override fun videoPickerDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.choose))
        builder.setPositiveButton(resources.getString(R.string.take_a_video)) { dialog, id ->
            dispatchTakeVideoIntent()
        }
        builder.setNegativeButton(resources.getString(R.string.choose_from_gallery)){ dialog, id ->
            galleryVideoIntent()
        }.show()
    }

    override fun sesionExpired() {
        baseActivity!!.sesionExpired()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) =
        baseActivity!!.requestPermissionsSafely(permissions, requestCode)

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String) = baseActivity!!.hasPermission(permission)

    fun navigateToExternalWebView(link: String){
        baseActivity?.navigateToExternalWebView(link)
    }

}
