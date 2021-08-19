package com.ai.tirios.ui.maintenance_bot

import com.ai.tirios.base.Medium

interface MaintenanceBotMedium: Medium {
    fun onAttachFile()
    fun onSendMsg()
    fun onMaintenanceIdUpdate(maintenanceId:Int, dataMsg:String)
    fun onMaintenanceUpdate(dataMsg:String)
}