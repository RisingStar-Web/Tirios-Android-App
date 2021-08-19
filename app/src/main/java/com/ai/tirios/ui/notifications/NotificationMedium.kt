package com.ai.tirios.ui.notifications

import com.ai.tirios.base.Medium

interface NotificationMedium: Medium {
    fun onBackArrowPressed()
}