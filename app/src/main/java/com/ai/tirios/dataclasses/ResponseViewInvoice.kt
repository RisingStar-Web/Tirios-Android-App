package com.ai.tirios.dataclasses

data class ResponseViewInvoice(
    val Amount: Double,
    val CreatedBy: Any,
    val CreatedOn: String,
    val DocumentContent: String,
    val DocumentLocation: String,
    val DocumentName: String,
    val DocumentType: Int,
    val Id: Int,
    val Invoice: Invoice,
    val InvoiceId: Int,
    val IsActive: Boolean,
    val ModifiedBy: Any,
    val ModifiedOn: Any
)