package com.ai.tirios.dataclasses

data class ServiceDetail(
    val Amount: Double,
    val Description: String,
    val MaintenanceRequestId: Int,
    val PricePerHour: Double,
    val Quantity: Int,
    val ServiceCategory: Int,
    val ServiceProviderId: Int
)