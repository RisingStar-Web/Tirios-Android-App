package com.ai.tirios.ui.contact_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.ai.tirios.R
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityContactUsBinding
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class ContactUsActivity : BaseActivity<ActivityContactUsBinding, ContactUsViewModel>(),
    ContactUsMedium {

    var binding: ActivityContactUsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.contactUsActivtity = this
        binding!!.contactUsViewModel = viewModel
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_contact_us

    override lateinit var viewModel: ContactUsViewModel
        @Inject internal set

    override val bindingVariable: Int
        get() = BR._all


    override fun onBackArrowPressed() {
        this.finish()
    }

    override fun onEmailclick() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:${getString(R.string.official_email_id)}")); // only email apps should handle this
        startActivity(intent)
    }

    override fun onPhoneNoClick() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:${getString(R.string.official_contat_no)}"))
        startActivity(intent)
    }

}