package com.ai.tirios.ui.maintenance

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MaintenanceFragmentProvider {

    @ContributesAndroidInjector(modules = [MaintenanceModule::class])
    internal abstract fun provideFragmentFactory(): MaintenanceFragment
}

