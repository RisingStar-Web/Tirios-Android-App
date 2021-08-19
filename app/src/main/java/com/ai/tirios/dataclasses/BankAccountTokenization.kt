package com.ai.tirios.dataclasses

data class BankAccountTokenization(
    val code: Int,
    val `data`: DataXXX,
    val errors: List<Any>,
    val success: Boolean
)