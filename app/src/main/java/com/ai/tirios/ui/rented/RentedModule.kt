package com.ai.tirios.ui.rented

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */
@Module
class RentedModule {
    @Provides
    internal fun ownedModule(dataManager: DataManager)= RentedViewModel(dataManager)
}