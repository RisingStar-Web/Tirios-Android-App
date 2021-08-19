package com.ai.tirios.dataclasses


import com.google.gson.annotations.SerializedName

data class BodyDownloadImage(
    val documentType: List<Int>,
    val mobile: String
)