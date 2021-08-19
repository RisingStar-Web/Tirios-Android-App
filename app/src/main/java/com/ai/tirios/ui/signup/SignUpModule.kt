package com.ai.tirios.ui.signup

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class SignUpModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = SignUpViewModel(dataManager)
}