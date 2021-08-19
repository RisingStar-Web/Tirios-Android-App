package com.ai.tirios.dataclasses

data class TenantProperty(
    val DueDate: Any,
    val Email: String,
    val FirstName: String,
    val IsActive: Boolean,
    val IsInvited: Boolean,
    val IsResiding: Boolean,
    val LastName: String,
    val Mobile: String,
    val NumberOfFamilyMembers: Int,
    val PaymentFrequency: Int,
    val Rent: Int,
    val RentDeposit: Int,
    val RentInvoiceId: Int,
    val SecondDueDate: Any,
    val Status: Int,
    val TenantDocuments: List<Any>,
    val TenantId: Int,
    val TenantStatus: String,
    val UserId: String,
    val UserName: String
)