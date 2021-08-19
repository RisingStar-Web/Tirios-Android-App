package com.ai.tirios.ui.maintenance_bot

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class MaintenanceBotModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = MaintenanceBotViewModel(dataManager)
}