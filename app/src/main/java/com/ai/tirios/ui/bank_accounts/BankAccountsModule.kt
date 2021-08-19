package com.ai.tirios.ui.bank_accounts

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class BankAccountsModule {
    @Provides
    internal fun viewModule(dataManager: DataManager) = BankAccountsViewModel(dataManager)
}