package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseResponse(
    val message: String,
    val status: Boolean

) : Parcelable{}
