package com.ai.tirios.dataclasses

data class ResponseAddCustomer(
    val code: Int,
    val `data`: DataXX,
    val errors: List<Any>,
    val success: Boolean
)