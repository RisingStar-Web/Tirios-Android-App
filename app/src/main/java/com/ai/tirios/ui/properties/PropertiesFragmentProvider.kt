package com.ai.tirios.ui.properties

import com.ai.tirios.ui.home.HomeFragment
import com.ai.tirios.ui.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
abstract class PropertiesFragmentProvider {
    @ContributesAndroidInjector(modules = [PropertiesModule::class])
    internal abstract fun provideFragmentFactory(): PropertiesFragment
}