package com.ai.tirios.ui.add_tenant_bot

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class AddTenantBotModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) =
        AddTenantBotViewModel(dataManager)
}