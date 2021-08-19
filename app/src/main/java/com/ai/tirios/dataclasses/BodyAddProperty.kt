package com.ai.tirios.dataclasses

data class BodyAddProperty(
    val city: String,
    val landlordEmail: String,
    val landlordFirstName: String,
    val landlordId: String,
    val landlordLastName: String,
    val landlordMobile: String,
    val mortgageAmount: Double,
    val mortgageDueDate: String?,
    val mortgageNumber: String,
    val name: String,
    val propertyTaxAmount: Double,
    val propertyTaxDueDate: String?,
    val propertyTaxNumber: String,
    val state: String,
    val street: String,
    val zipCode: String
)