package com.ai.tirios.dataclasses

data class ResponseBankAccounts(
    val code: Int,
    val `data`: Data,
    val errors: List<Any>,
    val success: Boolean
)