package com.ai.tirios.ui.forgot_password

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */
@Module
class ForgotPasswrodModule {
    @Provides
    internal fun ForgotPasswordViewModule(dataManager: DataManager) = ForgotPasswordViewModel(dataManager)
}