package com.ai.tirios.ui.register

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class RegisterModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = RegisterViewModel(dataManager)
}