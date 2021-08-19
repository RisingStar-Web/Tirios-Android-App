package com.ai.tirios.dataclasses

data class ServiceProvider(
    val BusinessLicenseNo: String,
    val City: String,
    val Documents: List<Documents>,
    val DrivingLicense: String,
    val Email: String,
    val ExpiryDate: String,
    val FirstName: String,
    val Id: Int,
    val InsuranceNo: String,
    val LastName: String,
    val Mobile: String,
    val PayrixCustId: String,
    val PayrixMerchantId: String,
    val Rating: Int,
    val ServiceCategory: String,
    val ServiceZipCodes: String,
    val Status: Int,
    val Street: String,
    val WorkingHours: Int,
    val ZipCode: String
)