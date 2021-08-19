package com.ai.tirios.ui.splash

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 07-05-2021.
 */
@Module
class SplashModule {
    @Provides
    internal fun splashViewModule(dataManager: DataManager)= SplashViewModel(dataManager)
}