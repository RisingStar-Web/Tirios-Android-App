package com.ai.tirios.dataclasses

data class BankAccount(
    val accountNumber: String,
    val customerId: String,
    val expiration: String,
    val firstName: String,
    val lastName: String,
    val merchantId: String,
    val method: Int,
    val routing: String,
    val token: String
){
    fun getLastFourDigitsInAccount(): String{
        return "XXXX-XXXX-XXXX-"+accountNumber
    }
}