package com.ai.tirios.ui.add_tenant

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 17-05-2021.
 */
@Module
class AddTenantModule {
    @Provides
    internal fun adTenantModule(dataManager: DataManager)= AddTenantViewModel(dataManager)
}