package com.ai.tirios.ui.home

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun provideFragmentFactory(): HomeFragment
}

