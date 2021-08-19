package com.ai.tirios.dataclasses

data class BodyPayWithBank(
    val dateOfAgreement: String,
    val ipAddress: String,
    val merchant: String,
    val order: String,
    val origin: String,
    val token: Token,
    val total: Int,
    val type: String
)