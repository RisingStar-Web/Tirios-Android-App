package com.ai.tirios.ui.error_message

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 11-05-2021.
 */
@Module
class ErrorMessageModule {
    @Provides
    internal fun errorModule(dataManager: DataManager)= ErrorMessageViewModel(dataManager)
}