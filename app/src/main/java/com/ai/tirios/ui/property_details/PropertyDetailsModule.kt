package com.ai.tirios.ui.property_details

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 15-05-2021.
 */
@Module
class PropertyDetailsModule {
    @Provides
    internal fun ownedModule(dataManager: DataManager)= PropertyDetailsViewModel(dataManager)
}