package com.ai.tirios.dataclasses

data class ResponseMerchant(
    val code: Int,
    val `data`: DataX,
    val errors: List<Any>,
    val success: Boolean
)