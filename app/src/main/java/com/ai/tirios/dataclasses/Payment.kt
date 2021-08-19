package com.ai.tirios.dataclasses

data class Payment(
    val method: Int,
    val number: String,
    val routing: String
)