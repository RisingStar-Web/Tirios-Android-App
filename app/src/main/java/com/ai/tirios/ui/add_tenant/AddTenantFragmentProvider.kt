package com.ai.tirios.ui.add_tenant

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
@Module
abstract class AddTenantFragmentProvider {
    @ContributesAndroidInjector(modules = [AddTenantModule::class])
    internal abstract fun provideFragmentFactory(): AddTenantFragment
}