package com.ai.tirios.ui.maintenance

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
class MaintenanceModule {
@Provides
internal fun homeModule(dataManager: DataManager)= MaintenanceViewModel(dataManager)
}