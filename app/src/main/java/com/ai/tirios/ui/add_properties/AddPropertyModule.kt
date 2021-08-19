package com.ai.tirios.ui.add_properties

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 20-05-2021.
 */
@Module
class AddPropertyModule {
    @Provides
    internal fun adTenantModule(dataManager: DataManager)= AddPropertyViewModel(dataManager)
}