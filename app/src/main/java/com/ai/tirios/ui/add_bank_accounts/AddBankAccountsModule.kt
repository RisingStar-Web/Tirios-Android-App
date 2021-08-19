package com.ai.tirios.ui.add_bank_accounts

import com.ai.tirios.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class AddBankAccountsModule {
    @Provides
    internal fun viewModule(dataManager: DataManager) = AddBankAccountsViewModel(dataManager)
}