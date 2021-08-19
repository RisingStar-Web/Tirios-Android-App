package com.ai.tirios.ui.forgot_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivityForgotPasswordBinding
import com.ai.tirios.dataclasses.BodyChangePassword
import com.ai.tirios.base.BaseActivity
import javax.inject.Inject

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(),
    ForgotPasswordMedium {
    var binding: ActivityForgotPasswordBinding? = null
    var mobile_number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.forgotPassword = viewModel

        mobile_number = intent.getStringExtra("CODE")+intent.getStringExtra("MOBILE")

        binding!!.etNewPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(isValidPassword(p0.toString())){
                    binding!!.validPassword = true
                }else{
                    binding!!.validPassword = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_forgot_password
    override lateinit var viewModel: ForgotPasswordViewModel
       @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onUpdateClick() {
        if (binding!!.etNewPassword.text.toString().isNullOrEmpty()){
            showToast(resources.getString(R.string.enter_new_password))
        }else if(!isValidPassword(binding!!.etNewPassword.text.toString())){
            showToast(resources.getString(R.string.password_regex))
        }else if (binding!!.etConfirmPassword.text.toString().isNullOrEmpty()){
            showToast(resources.getString(R.string.enter_confirm_password))
        } else if (!binding!!.etNewPassword.text!!.toString().equals(binding!!.etConfirmPassword.text.toString())) {
            showToast(resources.getString(R.string.passoword_not_match))
        }else{
            viewModel.changePassword(this, BodyChangePassword(mobile_number,
                binding!!.etNewPassword.text.toString())
            )
        }
    }

}
