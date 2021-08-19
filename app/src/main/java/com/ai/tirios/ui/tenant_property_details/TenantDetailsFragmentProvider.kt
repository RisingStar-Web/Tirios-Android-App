package com.ai.tirios.ui.tenant_property_details

import com.ai.tirios.ui.home.HomeFragment
import com.ai.tirios.ui.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
abstract class TenantDetailsFragmentProvider {
    @ContributesAndroidInjector(modules = [TenantDetailsModule::class])
    internal abstract fun provideFragmentFactory(): TenantDetailsFragment
}