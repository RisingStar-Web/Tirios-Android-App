package com.ai.tirios.dataclasses

data class ResponseCreditCard(
    val code: Int,
    val `data`: DataXXXX,
    val errors: List<Any>,
    val success: Boolean
)