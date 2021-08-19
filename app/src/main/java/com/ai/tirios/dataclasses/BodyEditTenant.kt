package com.ai.tirios.dataclasses

data class BodyEditTenant(
    val documentsUpdated: Boolean,
    val dueDate: String,
    val email: String,
    val firstName: String,
    val isActive: Boolean,
    val isInvited: Boolean,
    val isResiding: Boolean,
    val lastName: String,
    val mobile: String,
    val numberOfFamilyMembers: Int,
    val paymentFrequency: Int,
    val rent: Int,
    val rentDeposit: Int,
    val secondDueDate: String,
    val status: Int,
    val tenantPropertyId: Int,
    val userId: String
)