package com.ai.tirios.ui.send_otp

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class SendOtpModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = SendOtpViewModel(dataManager)
}