package com.ai.tirios.dataclasses

data class BodyEditProperty(
    val city: String,
    val mortgageAmount: Double,
    val mortgageDueDate: String?,
    val mortgageNumber: String,
    val name: String,
    val propertyId: Int,
    val propertyTaxAmount: Double,
    val propertyTaxDueDate: String?,
    val propertyTaxNumber: String,
    val state: String,
    val street: String,
    val zipCode: String
)