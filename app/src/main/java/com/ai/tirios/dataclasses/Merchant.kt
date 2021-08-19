package com.ai.tirios.dataclasses

data class Merchant(
    val mcc: String,
    val members: List<Member>,
    val status: Int
)