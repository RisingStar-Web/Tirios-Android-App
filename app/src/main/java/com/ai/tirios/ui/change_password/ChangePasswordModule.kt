package com.ai.tirios.ui.change_password

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class ChangePasswordModule {
    @Provides
    internal fun changePasswordModule(dataManager: DataManager) = ChangePasswordViewModel(dataManager)
}