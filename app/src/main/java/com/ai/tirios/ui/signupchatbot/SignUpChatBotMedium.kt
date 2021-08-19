package com.ai.tirios.ui.signupchatbot

import com.ai.tirios.base.Medium

interface SignUpChatBotMedium: Medium {
    fun onAttachFile()
    fun onSendMsg()
}