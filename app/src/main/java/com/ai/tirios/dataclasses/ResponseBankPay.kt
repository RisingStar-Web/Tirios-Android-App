package com.ai.tirios.dataclasses

data class ResponseBankPay(
    val code: Int,
    val `data`: DataXXXXX,
    val errors: List<Any>,
    val success: Boolean
)