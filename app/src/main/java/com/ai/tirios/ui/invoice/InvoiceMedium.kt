package com.ai.tirios.ui.invoice

import com.ai.tirios.base.Medium

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
interface InvoiceMedium: Medium {
    fun newPay()
    fun onBackArrowPressed()
}