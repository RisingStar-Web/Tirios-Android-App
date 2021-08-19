package com.ai.tirios.ui.contact_us

import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.data.DataManager

class ContactUsViewModel internal constructor(dataManager: DataManager)
    : BaseViewModel<ContactUsMedium>(dataManager){

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun onEmailclickPressed() = medium.onEmailclick()

    fun onPhoneClickPressed() = medium.onPhoneNoClick()

    }