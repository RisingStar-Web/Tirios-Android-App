package com.ai.tirios.dataclasses

/**
 * Created by Gaurav Kumar Tanwar on 28,May,2021
 * Noiad, U.P.
 * India
 */
data class ResponseUpdateProfile(
    val code: Int,
    val errors: List<Error>,
    val success: Boolean,
    val data: UserData
)
