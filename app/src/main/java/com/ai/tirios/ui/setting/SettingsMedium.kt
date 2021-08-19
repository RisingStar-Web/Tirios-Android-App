package com.ai.tirios.ui.setting

import com.ai.tirios.base.Medium

interface SettingsMedium : Medium{
    fun onBackArrowPressed()
    fun showSignOutDialog()
}