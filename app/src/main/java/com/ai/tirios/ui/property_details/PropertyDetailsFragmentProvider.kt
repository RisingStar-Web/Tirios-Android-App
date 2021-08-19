package com.ai.tirios.ui.property_details

import com.ai.tirios.ui.home.HomeFragment
import com.ai.tirios.ui.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
abstract class PropertyDetailsFragmentProvider {
    @ContributesAndroidInjector(modules = [PropertyDetailsModule::class])
    internal abstract fun provideFragmentFactory(): PropertyDetailsFragment
}