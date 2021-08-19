package com.ai.tirios.dataclasses

data class UpdateRent(
    val balanceDue: Int,
    val invoiceId: Int,/*
    val paymentDate: String,*/
    val paymentProcessed: Int,
    val paymentstatus: Int,
    val paymentstatusValue: String
)