package com.ai.tirios.ui.invoice

import com.ai.tirios.data.DataManager
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
class InvoiceViewModel internal constructor(dataManager: DataManager): BaseViewModel<InvoiceMedium>(dataManager) {

    fun newPay() = medium.newPay()

    fun onBackArrowPressed() = medium.onBackArrowPressed()
}