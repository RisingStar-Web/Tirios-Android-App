package com.ai.tirios.ui.invoice

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class InvoiceFragmentProvider {

    @ContributesAndroidInjector(modules = [InvoiceModule::class])
    internal abstract fun provideFragmentFactory(): InvoiceFragment
}

