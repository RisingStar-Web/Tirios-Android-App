package com.ai.tirios.ui.maintenance_details

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
class MaintenanceDetailsModule {
@Provides
internal fun homeModule(dataManager: DataManager)= MaintenanceDetailsViewModel(dataManager)
}