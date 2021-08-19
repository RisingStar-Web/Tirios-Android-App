package com.ai.tirios.ui.send_otp

import com.ai.tirios.base.Medium

interface SendOtpMedium: Medium {
    fun onSendOtp()
    fun onResendOtp()
}