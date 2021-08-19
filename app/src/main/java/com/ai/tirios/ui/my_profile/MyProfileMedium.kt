package com.ai.tirios.ui.my_profile

import com.ai.tirios.base.Medium

/**
 * Created by Ashish Kadam on 27,May,2021
 */
interface MyProfileMedium :Medium {
    /*.......Back Button Press Function.....*/
    fun onBackArrowPressed()

    /*.......change password Button Click Function......*/
    fun onChnagePasswordClick()

    /*.......Update UserInfo Function............*/
    fun onSaveChangesClick()


}