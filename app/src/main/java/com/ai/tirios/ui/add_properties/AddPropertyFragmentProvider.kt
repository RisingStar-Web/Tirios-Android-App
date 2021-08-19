package com.ai.tirios.ui.add_properties

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
abstract class AddPropertyFragmentProvider {
    @ContributesAndroidInjector(modules = [AddPropertyModule::class])
    internal abstract fun provideFragmentFactory(): AddPropertyFragment
}