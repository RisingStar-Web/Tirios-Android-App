package com.ai.tirios.ui.checkout_prices

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 08-05-2021.
 */
@Module
class CheckoutPricesModule {
    @Provides
    internal fun CheckoutPricesViewModule(dataManager: DataManager) = CheckoutPricesViewModel(dataManager)
}