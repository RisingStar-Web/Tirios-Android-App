package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTenantImageUpload(
    val Document: String,
    val DocumentCategory: Int,
    val DocumentCategoryName: String,
    val DocumentExtension: String,
    val DocumentName: String,
    val DocumentURL: String,
    val TenantPropertyId: Int
): Parcelable{}