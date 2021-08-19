package com.ai.tirios.ui.landlord_login

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */
@Module
class LandLordModule {
    @Provides
    internal fun LandLordViewModule(dataManager: DataManager) = LandLordViewModel(dataManager)
}