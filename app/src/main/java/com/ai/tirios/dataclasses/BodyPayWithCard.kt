package com.ai.tirios.dataclasses

data class BodyPayWithCard(
    val dateOfAgreement: String,
    val ipAddress: String,
    val merchant: String,
    val order: String,
    val origin: String,
    val token: String,
    val total: Int,
    val type: String
)