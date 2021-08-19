package com.ai.tirios.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivitySettingsBinding
import com.ai.tirios.dataclasses.BodyDownloadImage
import com.ai.tirios.dataclasses.BodyUploadFile
import com.ai.tirios.ui.bank_accounts.BankAccountsActivity
import com.ai.tirios.ui.change_password.ChangePasswordActivity
import com.ai.tirios.ui.contact_us.ContactUsActivity
import com.ai.tirios.ui.credit_card.MyCardDetailsActivity
import com.ai.tirios.ui.my_profile.MyProfileActivity
import com.ai.tirios.ui.navigation.NavigationActivity
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.Utilities
import com.bumptech.glide.Glide
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class SettingsActivity : BaseActivity<ActivitySettingsBinding, SettingsViewModel>(),
    SettingsMedium {

    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set
    private var binding: ActivitySettingsBinding? = null
    private lateinit var settingsAdapter: SettingsAdapter
    private var imageBitmapBase64 = ""
    var sharedStorage: SharedStorage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.settings = viewModel
        sharedStorage = SharedStorage(this)

        binding!!.tvUserName.text = sharedStorage!!.getfirstName()

        viewModel.bankAccounts.observe(this, androidx.lifecycle.Observer {
            if (it.data.bankAccounts.size != 0){
                setRecyclerView(true)
            }else{
                setRecyclerView(false)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getBankAccounts(sharedStorage!!.getid()!!)
        downloadProfilePhoto(viewModel)
        uploadProfilePhoto(viewModel)
    }

    override fun onClick(v: View?) {
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_settings
    override lateinit var viewModel: SettingsViewModel
        @Inject internal set

    private fun downloadProfilePhoto(viewModel: SettingsViewModel) {
        binding?.tvUserName?.text = sharedStorage?.getfirstName()
        viewModel.downloadImage(BodyDownloadImage(listOf(4), (sharedStorage!!.getusername() ?: "")))
    }

    private fun uploadProfilePhoto(viewModel: SettingsViewModel) {
        viewModel.downloadedImage.observe(this , Observer{
            Glide.with(this).load(it).circleCrop().into(binding!!.imageViewProfilePic)
        })

        viewModel.imageBitmapBase64Upload.observe(this, androidx.lifecycle.Observer {
            if (!imageBitmapBase64.equals("")) {
                viewModel.uploadImage(
                    BodyUploadFile(
                        imageBitmapBase64, "jpg", 1, 4,
                        "", (sharedStorage!!.getusername() ?: ""), "string"
                    )
                )
            }
        })
    }

    private fun setRecyclerView(hasBankAccount : Boolean) {
        val array: MutableList<String> = mutableListOf()
        array.addAll(resources.getStringArray(R.array.settings_list))
        if (hasBankAccount)
            array.add(2, resources.getString(R.string.add_credit_cards))
        settingsAdapter =
            SettingsAdapter(viewModel.getSettingsList(array)) {
                val settingList = array
                when (it.name) {
                    "My Profile" -> navigateTo(MyProfileActivity::class.java)
                    "Change Password" -> navigateTo(ChangePasswordActivity::class.java)
                    "Bank Accounts" -> navigateTo(BankAccountsActivity::class.java)
                    "Share App" -> viewModel.shareTirios(resources.getString(R.string.tirios_share_msg))
                    "Contact Us" -> navigateTo(ContactUsActivity::class.java)
                    "Sign Out" -> showSignOutDialog()
                    "Add Credit Cards" -> navigateTo(MyCardDetailsActivity::class.java)
                }
            }
        binding?.let { binding ->
            binding.recyclerView.adapter = settingsAdapter
            binding.recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onBackArrowPressed() {
        this.finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.SharedPref.CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //dispatchTakePictureIntent()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showSignOutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(resources.getString(R.string.msgSignOut))
        builder.setTitle(resources.getString(R.string.alert))
        builder.setPositiveButton(resources.getString(R.string.btnOk)) { dialog, id ->
            viewModel.signOut()
            navigateTo(NavigationActivity::class.java, true, Bundle(), Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        builder.setNegativeButton(resources.getString(R.string.btnCancel)) { dialog, id ->
            dialog.dismiss()
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Constants.SharedPref.REQUEST_IMAGE_CAPTURE && data != null) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            binding!!.imageViewProfilePic.setImageBitmap(imageBitmap)
            Glide.with(this).load(imageBitmap).circleCrop().into(binding!!.imageViewProfilePic)
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitmap)!!
//            viewModel.imageBitmapBase64Upload.value = imageBitmapBase64
        } else if (resultCode == RESULT_OK && requestCode == Constants.SharedPref.GALLERY_IMAGE_CAPTURE && data != null) {

            val selectedImageUri: Uri = data!!.data!!
            Glide.with(this).load(selectedImageUri).circleCrop().into(binding!!.imageViewProfilePic)
            binding!!.imageViewProfilePic.setImageURI(selectedImageUri)
            val imageBitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitmap)!!
        }
        viewModel.imageBitmapBase64Upload.value = imageBitmapBase64
    }
}