package com.ai.tirios.ui.credit_card

import com.ai.tirios.data.DataManager
import com.ai.tirios.ui.credit_card.add_card.AddCardViewModel
import dagger.Module
import dagger.Provides

@Module
class MyCardModule {
    @Provides
    internal fun myModule(dataManager: DataManager) = MyCardDetailsViewModel(dataManager)
}