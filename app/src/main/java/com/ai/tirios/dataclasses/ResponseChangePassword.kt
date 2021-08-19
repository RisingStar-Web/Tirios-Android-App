package com.ai.tirios.dataclasses


import com.google.gson.annotations.SerializedName

data class ResponseChangePassword(
    val code: Int,
    val data: Any,
    val errors: List<Error>,
    val success: Boolean
)