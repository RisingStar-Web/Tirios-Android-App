package com.ai.tirios.dataclasses

data class UpdateInvoice(
    val balanceDue: Int,
    val invoiceId: Int,
    val paymentProcessed: Int,
    val paymentstatus: Int,
    val paymentstatusValue: String,
    val paymentDate: String
)