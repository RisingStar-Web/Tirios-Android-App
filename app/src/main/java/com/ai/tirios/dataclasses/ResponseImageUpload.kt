package com.ai.tirios.dataclasses

data class ResponseImageUpload(
    val Document: String,
    val DocumentExtension: String,
    val DocumentName: String,
    val DocumentURL: String,
    val PropertyId: Int
)