package com.ai.tirios.ui.select_payment

import com.ai.tirios.base.Medium

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */
interface SelectPaymentMedium: Medium {
    fun onBackPress()
    fun Pay()
}