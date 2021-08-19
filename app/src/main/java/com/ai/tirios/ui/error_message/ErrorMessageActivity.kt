package com.ai.tirios.ui.error_message

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivityErrorMessageBinding
import com.ai.tirios.ui.navigation.NavigationActivity
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import javax.inject.Inject

class ErrorMessageActivity : BaseActivity<ActivityErrorMessageBinding, ErrorMessageViewModel>(), ErrorMessageMedium {
    var binding: ActivityErrorMessageBinding? = null
    var sharedStorage: SharedStorage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        binding!!.errorMessage = viewModel
        viewModel.medium = this
        sharedStorage = SharedStorage(this)

        if (intent.getStringExtra("status") == "Pending"){
            binding!!.message = resources.getString(R.string.our_team_is_verifying_your_details_and_will_get_back)
        }else if (intent.getStringExtra("status") == "Rejected"){
            binding!!.message = resources.getString(R.string.unfortunately_your_details_have_failed_the_verification)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_error_message
    override lateinit var viewModel: ErrorMessageViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onBackPressed() {
        navigateTo(
            NavigationActivity::class.java, false, Bundle(),
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun logout() {
        val deviceToken = SharedStorage(this).getDeviceId()
        sharedStorage!!.clearStorage()
        deviceToken?.let { SharedStorage(this).setDeviceId(it) }
        onBackPressed()
    }
}