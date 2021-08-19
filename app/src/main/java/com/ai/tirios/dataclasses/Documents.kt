package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Documents(
    val Document: String,
    val DocumentExtension: String,
    val DocumentName: String,
    val DocumentURL: String,
    val PropertyId: Int
): Parcelable{}