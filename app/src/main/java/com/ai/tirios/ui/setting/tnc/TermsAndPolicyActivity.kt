package com.ai.tirios.ui.setting.tnc

import android.os.Bundle
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivityTermsPolicyBinding
import com.ai.tirios.ui.setting.SettingsMedium
import com.ai.tirios.ui.setting.SettingsViewModel
import com.ai.tirios.base.BaseActivity
import javax.inject.Inject


class TermsAndPolicyActivity : BaseActivity<ActivityTermsPolicyBinding, SettingsViewModel>(),
    SettingsMedium {
    var binding: ActivityTermsPolicyBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.terms = viewModel
        getAndSetData()
    }

    private fun getAndSetData() {
        intent.getStringExtra("FILE_PATH")?.let {
            binding?.pdfView?.fromAsset(it)?.autoSpacing(false)?.spacing(6)?.pageFling(false)?.load()
            binding?.pdfView?.setBackgroundColor(resources.getColor(R.color.gray))
        }
        intent.getStringExtra("FILE_TITLE")?.let {
            binding?.condtionTitle?.text = it
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_terms_policy
    override lateinit var viewModel: SettingsViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onBackArrowPressed() {
        this.finish()
    }

    override fun showSignOutDialog() {
        TODO("Not yet implemented")
    }

}
