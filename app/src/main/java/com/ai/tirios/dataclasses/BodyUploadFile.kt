package com.ai.tirios.dataclasses


import com.google.gson.annotations.SerializedName

data class BodyUploadFile(
    val document: String,
    val documentExtension: String,
    val documentSide: Int,
    val documentType: Int,
    val documentURL: String,
    val mobile: String,
    val s3FileName: String
)