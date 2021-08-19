package com.ai.tirios.ui.credit_card.add_card

import com.ai.tirios.base.Medium

interface AddCardMedium: Medium {

    fun onBackArrowPressed()

    fun addCreditCard()
}