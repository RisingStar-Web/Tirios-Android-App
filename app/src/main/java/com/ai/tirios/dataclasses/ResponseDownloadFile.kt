package com.ai.tirios.dataclasses


import com.google.gson.annotations.SerializedName

data class ResponseDownloadFile(
    val detail: String,
    val status: Int,
    val title: String,
    val traceId: String,
    val type: String
)