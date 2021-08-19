package com.ai.tirios.ui.main

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 11-05-2021.
 */
@Module
class MainModule {
    @Provides
    internal fun mainModule(dataManager: DataManager)= MainActivityViewModel(dataManager)
}