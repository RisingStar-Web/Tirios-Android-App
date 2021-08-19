package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11-05-2021.
 */
@Parcelize
data class BodyChangePassword(
    var mobile: String,
    var password: String
):Parcelable {}
