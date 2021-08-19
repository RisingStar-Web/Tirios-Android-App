package com.ai.tirios.ui.owned

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */
@Module
class OwnedModule {
    @Provides
    internal fun ownedModule(dataManager: DataManager)= OwnedViewModel(dataManager)
}