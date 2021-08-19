package com.ai.tirios.ui.my_profile

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityMyProfileBinding
import com.ai.tirios.dataclasses.BodyUpdateMyProfile
import com.ai.tirios.ui.change_password.ChangePasswordActivity
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class MyProfileActivity : BaseActivity<ActivityMyProfileBinding, MyProfileViewModel>(),
    MyProfileMedium {

    private var binding: ActivityMyProfileBinding? = null
    private var sharedStorage: SharedStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.myProfile = viewModel
        sharedStorage = SharedStorage(this)
        setUserData()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_my_profile

    override lateinit var viewModel: MyProfileViewModel
        @Inject internal set


    override val bindingVariable: Int
        get() = BR._all


    override fun onBackArrowPressed() {
        this.finish()
    }

    override fun onChnagePasswordClick() {
        navigateTo(ChangePasswordActivity::class.java)
    }

    override fun onSaveChangesClick() {


        when {
            TextUtils.isEmpty(binding!!.etEmail.text.toString()) -> {
                showToast(this.resources.getString(R.string.please_enter_email_address))
            }
            !utilities?.isValidEmail(binding!!.etEmail.text.toString())!! -> {
                showToast(this.resources.getString(R.string.please_enter_valid_email_address))
            }
            else -> {
                viewModel.UpdateProfile(
                    BodyUpdateMyProfile(
                        sharedStorage!!.getusername().toString(),
                        binding!!.etEmail.text.toString(),
                        sharedStorage!!.getfirstName().toString(),
                        sharedStorage!!.getlastName().toString(),
                        sharedStorage!!.isDocumentsUploaded()
                    )
                )
            }
        }


    }

    private fun setUserData() {
        sharedStorage?.let { storage->
            binding!!.etFirstName.text = storage.getfirstName()
            binding!!.etLastName.text = storage.getlastName()
            binding!!.etEmail.setText(storage.getemail())
            binding!!.etPhoneNo.text = PhoneNumberUtils.formatNumber(storage.getusername())
            var countrycode = sharedStorage!!.getusername()!!
                .substring(0, sharedStorage!!.getusername()!!.length-10)
            binding!!.ccp.isClickable = false
            if(countrycode.contains("+"))
                binding!!.ccp.setCountryForPhoneCode(countrycode.replace("+","").toInt())
            //storage.getCountryFlag()?.let { binding!!.ccp.setCountryForNameCode(it) }
        }
    }
}