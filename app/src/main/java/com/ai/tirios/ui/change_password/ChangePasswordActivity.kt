package com.ai.tirios.ui.change_password

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ChangePasswordFragmentBinding
import com.ai.tirios.dataclasses.BodyModifyPassword
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.regex.Pattern
import javax.inject.Inject

class ChangePasswordActivity :
    BaseActivity<ChangePasswordFragmentBinding, ChangePasswordViewModel>(), ChangePasswordMedium,
    HasSupportFragmentInjector {

    var mDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
        @Inject internal set
    private var binding: ChangePasswordFragmentBinding? = null
    var sharedStorage: SharedStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.changePassword = viewModel
        sharedStorage = SharedStorage(this)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.change_password_fragment
    override lateinit var viewModel: ChangePasswordViewModel
        @Inject internal set

    override fun onBackArrowPressed() {
        finish()
    }

    override fun changePassword() =
        when {
            TextUtils.isEmpty(binding!!.etCurrentPassword.text.toString()) -> {
                showToast(this.resources.getString(R.string.please_enter_current_password))
            }
            TextUtils.isEmpty(binding!!.etNewPassword.text.toString()) -> {
                showToast(this.resources.getString(R.string.please_enter_new_password))
            }
            TextUtils.isEmpty(binding!!.etConfirmPassword.text.toString()) -> {
                showToast(this.resources.getString(R.string.please_enter_confirm_password))
            }
            binding!!.etNewPassword.text.toString() != binding!!.etConfirmPassword.text.toString() -> {
                showToast(this.resources.getString(R.string.passwords_do_not_match))
            }
            else -> {
                val pattern =
                    Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$")
                val matcher = pattern.matcher(binding!!.etNewPassword.text.toString())
                if (matcher.matches()) {
                    with(viewModel) {
                        changePassword(
                            BodyModifyPassword(sharedStorage?.getusername() ?: "",
                                binding!!.etCurrentPassword.text.toString(),
                                binding!!.etNewPassword.text.toString()
                            )
                        )
                    }
                } else {
                    showToast(this.resources.getString(R.string.password_validation))
                }
            }
        }

    override fun supportFragmentInjector() = mDispatchingAndroidInjector

}