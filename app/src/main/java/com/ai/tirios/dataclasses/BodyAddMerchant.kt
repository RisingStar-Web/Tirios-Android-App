package com.ai.tirios.dataclasses

data class BodyAddMerchant(
    val accounts: List<Account>,
    val address1: String,
    val city: String,
    val country: String,
    val currency: String,
    val dateOfAgreement: String,
    val email: String,
    val ipAddress: String,
    val merchant: Merchant,
    val name: String,
    val phone: String,
    val state: String,
    val tiriosUserId: String,
    val type: Int,
    val zip: String
)