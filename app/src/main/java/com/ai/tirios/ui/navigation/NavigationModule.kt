package com.ai.tirios.ui.navigation

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = NavigationViewModel(dataManager)
}