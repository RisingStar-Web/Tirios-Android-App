package com.ai.tirios.ui.signupchatbot

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class SignUpChatBotModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = SignUpChatBotViewModel(dataManager)
}