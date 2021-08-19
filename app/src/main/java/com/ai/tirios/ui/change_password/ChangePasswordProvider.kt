package com.ai.tirios.ui.change_password

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChangePasswordProvider {

    @ContributesAndroidInjector(modules = [ChangePasswordModule::class])
    internal abstract fun provideFragmentFactory(): ChangePasswordActivity
}