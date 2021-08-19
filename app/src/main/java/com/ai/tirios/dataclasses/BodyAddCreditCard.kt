package com.ai.tirios.dataclasses

data class BodyAddCreditCard(
    val customer: String,
    val expiration: String,
    val payment: PaymentX
)