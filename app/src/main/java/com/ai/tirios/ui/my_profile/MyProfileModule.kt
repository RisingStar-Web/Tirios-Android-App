package com.ai.tirios.ui.my_profile

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

/**
 * Created by Ashish Kadam on 27,May,2021
 */
@Module
class MyProfileModule {

    @Provides
    internal fun MyProfileModule(dataManager: DataManager)=MyProfileViewModel(dataManager)

}