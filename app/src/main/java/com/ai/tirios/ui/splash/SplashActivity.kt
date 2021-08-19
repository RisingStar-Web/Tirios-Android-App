package com.ai.tirios.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.ActivitySplashBinding
import com.ai.tirios.ui.main.MainActivity
import com.ai.tirios.ui.navigation.NavigationActivity
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.ui.notification.TFirebaseMessagingService.Companion.EXTRA_NOTIFICATION
import com.ai.tirios.ui.notifications.NotificationActivity
import com.ai.tirios.utility.CommonUtility
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashMedium {

    var binding: ActivitySplashBinding? = null
    var sharedStorage: SharedStorage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = dataBinding
        viewModel.medium = this
        CommonUtility.getHashKey(this)
//        sharedStorage = SharedStorage(this)
        Handler().postDelayed({
            var sharedStorage = SharedStorage(this)
            if (sharedStorage.getid()!!.isNotEmpty() && intent.hasExtra(EXTRA_NOTIFICATION)) {
                navigateTo(NotificationActivity::class.java)
            } else if(sharedStorage.getid()!!.isNotEmpty())
                navigateTo(MainActivity::class.java)
            else
                navigateTo(NavigationActivity::class.java)
            finish()
        }, 3000)  // 3000 is the delayed time in milliseconds.
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_splash

    override lateinit var viewModel: SplashViewModel
        @Inject internal set

    override val bindingVariable: Int
        get() = BR._all

}