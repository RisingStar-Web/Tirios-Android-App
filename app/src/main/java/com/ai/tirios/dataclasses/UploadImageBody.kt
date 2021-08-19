package com.ai.tirios.dataclasses

data class UploadImageBody(
    val document: String,
    val documentExtension: String,
    val documentName: String,
    val documentType: Int,
    val tenantPropertyId: Int
)