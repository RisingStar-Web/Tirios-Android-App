package com.ai.tirios.ui.add_tenant_bot

import com.ai.tirios.base.Medium

interface AddTenantBotMedium: Medium {
    fun onAttachFile()
    fun onSendMsg()
    fun onAddTenantImageUpload(dataMsg:String, url : String)
}