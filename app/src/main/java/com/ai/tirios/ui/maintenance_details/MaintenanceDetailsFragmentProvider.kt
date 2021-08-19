package com.ai.tirios.ui.maintenance_details

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MaintenanceDetailsFragmentProvider {

    @ContributesAndroidInjector(modules = [MaintenanceDetailsModule::class])
    internal abstract fun provideFragmentFactory(): MaintenanceDetailsFragment
}

