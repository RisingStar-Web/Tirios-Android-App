package com.ai.tirios.ui.change_password

import com.ai.tirios.base.Medium

interface ChangePasswordMedium: Medium {
    fun onBackArrowPressed()
    fun changePassword()
}