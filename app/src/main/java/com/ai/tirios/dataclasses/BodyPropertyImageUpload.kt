package com.ai.tirios.dataclasses

data class BodyPropertyImageUpload(
    val document: String,
    val documentExtension: String,
    val documentName: String,
    val propertyId: Int
)