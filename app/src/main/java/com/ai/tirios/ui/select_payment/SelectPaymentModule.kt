package com.ai.tirios.ui.select_payment

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */
@Module
class SelectPaymentModule {
    @Provides
    internal fun ownedModule(dataManager: DataManager)= SelectPaymentViewModel(dataManager)
}