package com.ai.tirios.dataclasses

data class BodyAddCustomer(
    val customerDetail: CustomerDetail,
    val dateOfAgreement: String,
    val ipAddress: String,
    val tiriosUserId: String
)