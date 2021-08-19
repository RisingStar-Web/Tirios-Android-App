package com.ai.tirios.dataclasses

data class ResponseNotifications(val aps :Notification)

data class Notification(val alert :NotificationData)

data class NotificationData(
    val notificaionId : Int,
    val title : String,
    val body:String,
    val dateTime:String
)