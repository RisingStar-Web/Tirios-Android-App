package com.ai.tirios.ui.contact_us

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class ContactUsModule {

    @Provides
    internal fun ContactUsModule(dataManager: DataManager) = ContactUsViewModel(dataManager)

}