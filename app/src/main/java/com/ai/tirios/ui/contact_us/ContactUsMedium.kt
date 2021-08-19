package com.ai.tirios.ui.contact_us

import com.ai.tirios.base.Medium

interface ContactUsMedium :Medium {

    /*.......Back Button Press Function.....*/
    fun onBackArrowPressed()


    /*.......on email text click..........*/
    fun onEmailclick()


    /*.......on phone text click.........*/
    fun onPhoneNoClick()

}