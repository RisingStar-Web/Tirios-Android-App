package com.ai.tirios.ui.tenant_property_details

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */
@Module
class TenantDetailsModule {
    @Provides
    internal fun tenantModule(dataManager: DataManager)= TenantDetailsViewModel(dataManager)
}