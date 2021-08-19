package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BodyModifyPassword(
    var userName: String,
    var currentPassword: String,
    var newPassword: String
) : Parcelable