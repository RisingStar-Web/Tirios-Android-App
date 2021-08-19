package com.ai.tirios.ui.notifications

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class NotificationModule {
    @Provides
    internal fun notificationModule(dataManager: DataManager) = NotificationViewModel(dataManager)
}
