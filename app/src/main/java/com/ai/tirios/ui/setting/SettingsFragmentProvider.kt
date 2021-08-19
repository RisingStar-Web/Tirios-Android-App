package com.ai.tirios.ui.setting

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsFragmentProvider {

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    internal abstract fun provideFragmentFactory(): SettingsActivity
}