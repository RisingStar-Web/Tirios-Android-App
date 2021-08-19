package com.ai.tirios.dataclasses

data class DataX(
    val accountClosureReasonCode: Any,
    val accountClosureReasonDate: Any,
    val amex: Any,
    val annualACHSaleVolume: Any,
    val annualCCSaleVolume: Any,
    val annualCCSales: Int,
    val autoBoarded: Any,
    val avgTicket: Int,
    val boarded: Any,
    val chargebackNotificationEmail: Any,
    val chargebackRatio: Any,
    val creditRatio: Any,
    val creditTimeliness: Any,
    val dba: Any,
    val disclosureDate: Any,
    val disclosureIP: Any,
    val discover: Any,
    val entity: String,
    val environment: String,
    val established: Any,
    val frozen: Int,
    val inactive: Int,
    val lastActivity: Any,
    val letterDate: Any,
    val letterStatus: Int,
    val mcc: String,
    val members: List<MemberX>,
    val merchantId: String,
    val ndxDays: Any,
    val ndxPercentage: Any,
    val new: Int,
    val qsa: Any,
    val riskLevel: Any,
    val saqDate: Any,
    val saqType: Any,
    val status: String,
    val statusReason: Any,
    val tcAttestation: Int,
    val tcDate: Any,
    val tcVersion: Int,
    val totalApprovedSales: Int,
    val visaDisclosure: Int,
    val visaMvv: Any
)