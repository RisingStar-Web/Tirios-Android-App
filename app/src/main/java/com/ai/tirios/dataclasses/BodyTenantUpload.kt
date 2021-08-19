package com.ai.tirios.dataclasses

data class BodyTenantUpload(
    val document: String,
    val documentCategory: Int,
    val documentCategoryName: String,
    val documentExtension: String,
    val documentName: String,
    val tenantPropertyId: Int
)