package com.ai.tirios.base

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.ui.landlord_login.LandLordLoginActivity
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.Constants.SharedPref.Companion.CAMERA_REQUEST_CODE
import com.ai.tirios.utils.Constants.SharedPref.Companion.GALLERY_IMAGE_CAPTURE
import com.ai.tirios.utils.Constants.SharedPref.Companion.GALLERY_VIDEO_CAPTURE
import com.ai.tirios.utils.Constants.SharedPref.Companion.REQUEST_IMAGE_CAPTURE
import com.ai.tirios.utils.Utilities
import dagger.android.AndroidInjection
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Created by Maruthi Ram Yadav on 10/9/2020.
 */

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel<*>>: AppCompatActivity(), View.OnClickListener,
    Medium, BaseFragment.Callback{

    var dataBinding: DB? = null
        private set
    private var mProgressDialog: ProgressDialog? = null

    @get:LayoutRes
    abstract val layoutId: Int

    private var mViewModel: VM? = null

    abstract val viewModel: VM

    abstract val bindingVariable: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = when (mViewModel) {
            null -> viewModel
            else -> mViewModel
        }
        dataBinding!!.setVariable(bindingVariable, mViewModel)
        dataBinding!!.executePendingBindings()
    }

    override var utilities: Utilities? = null
        @Inject internal set

    override fun onFragmentAttached() = Unit

    override fun onFragmentDetached(tag: String) = Unit

    override val myActivity: FragmentActivity
        get() = this

    override fun getStringRes(resId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun showToast(message: String)= Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showToast(resId: Int) = showToast(getStringRes(resId)!!)

    override fun showErrorToast(message: String) {
        TODO("Not yet implemented")
    }

    override fun showAlert(message: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)

        // set the custom layout
        val view = layoutInflater.inflate(R.layout.activity_alert_dialog, null)

        // Get Views references from your layout

        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDetail: TextView = view.findViewById(R.id.tv_detail)
        val btDone: Button = view.findViewById(R.id.bt_done)

        tvTitle.setText(R.string.alert);
        tvDetail.setText(message)

        btDone.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })

        builder.setView(view)
        // create and show the alert dialog
        dialog = builder.create()

        dialog.show()

    }

    override fun showAlert(resId: Int) {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(view: View, message: String) {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(message: String) {
        TODO("Not yet implemented")
    }

    override fun showProgressbar() {
        dismissProgressbar()
        if (isFinishing.not()) {
            mProgressDialog = ProgressDialog(this).apply {
                show()
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_progress)
                isIndeterminate = true
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                show()
                Handler().postDelayed({
                    setCancelable(true)
                    setCanceledOnTouchOutside(true)
                }, (2 * 60 * 1000).toLong())
            }
        }
    }

    override fun dismissProgressbar() {
        mProgressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun showNetworkMessage() {
        TODO("Not yet implemented")
    }

    override fun logoutUser() {
        val deviceToken = SharedStorage(this).getDeviceId()
        SharedStorage(this).clearStorage()
        deviceToken?.let { SharedStorage(this).setDeviceId(it) }
    }

    override fun navigateTo(c: Class<*>, isFinishActivity: Boolean, bundle: Bundle, flag: Int?) {
        var intent = Intent(this, c)
        if (bundle != null) intent.putExtras(bundle)
        if (flag != null) {
            intent.addFlags(flag)
        }
        startActivity(intent)
        /*overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )*/
        if (isFinishActivity) finish()
    }

    override fun finishScreen() {
        finish()
    }

    override fun changeFragment(frag: Fragment, tagFragmentName: String, id: Int, bundle: Bundle) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
        frag.arguments = bundle
        fragmentTransaction.replace(id, frag, tagFragmentName)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun dispatchTakePictureIntent() {
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
                android.Manifest.permission.READ_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
        }
    }

    override fun dispatchTakeVideoIntent(){
        if (hasPermission(android.Manifest.permission.CAMERA)){
            val takePictureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
            val maxVideoSize = (10 * 1024 * 1024).toLong() // 10 MB
            takePictureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, maxVideoSize)
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }else{
            requestPermissionsSafely(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
        }
    }

    override fun galleryVideoIntent() {
        if (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            try {
                startActivityForResult(Intent.createChooser(intent, "Select Video"), GALLERY_VIDEO_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }else{
            requestPermissionsSafely(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE), CAMERA_REQUEST_CODE)
        }
    }

    override fun imagePickerDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.choose))
        builder.setPositiveButton(resources.getString(R.string.take_a_photo)) { dialog, id ->
            dispatchTakePictureIntent()
        }
        builder.setNegativeButton(resources.getString(R.string.choose_from_gallery)){ dialog, id ->
            galleryPictureIntent()
        }.show()
    }

    override fun videoPickerDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.choose))
        builder.setPositiveButton(resources.getString(R.string.take_a_video)) { dialog, id ->
            dispatchTakeVideoIntent()
        }
        builder.setNegativeButton(resources.getString(R.string.choose_from_gallery)){ dialog, id ->
            galleryVideoIntent()
        }.show()
    }

    override fun commonDialog(layoutDialog: Int, isCancelable: Boolean): Dialog {
        TODO("Not yet implemented")
    }

    override fun sesionExpired() {
        var sharedStorage = SharedStorage(this)
        sharedStorage.clearStorage()
        navigateTo(
            LandLordLoginActivity::class.java, false, Bundle(),
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String) =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    fun navigateToExternalWebView(link: String){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(if (link.contains("http")) link else "https://" + link)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome browser presumably not installed and open Kindle Browser
            intent.setPackage("com.amazon.cloud9")
            startActivity(intent)
        }
    }

    open fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

}
