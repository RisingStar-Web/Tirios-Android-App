package com.ai.tirios.dataclasses

data class ResponseOtp(
    val code: Int,
    val data: UserData,
    val errors: List<Error>,
    val success: Boolean
)