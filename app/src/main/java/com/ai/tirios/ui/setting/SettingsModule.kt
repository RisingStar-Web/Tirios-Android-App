package com.ai.tirios.ui.setting

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {
@Provides
internal fun settingsModule(dataManager: DataManager) = SettingsViewModel(dataManager)
}