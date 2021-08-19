package com.ai.tirios.ui.credit_card

import com.ai.tirios.data.DataManager
import com.ai.tirios.ui.credit_card.add_card.AddCardViewModel
import dagger.Module
import dagger.Provides

@Module
class AddCardModule {
    @Provides
    internal fun viewModule(dataManager: DataManager) = AddCardViewModel(dataManager)
}