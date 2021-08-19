package com.ai.tirios.ui.properties

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
class PropertiesModule {
    @Provides
    internal fun propertiesModule(dataManager: DataManager)= PropertiesViewModel(dataManager)
}