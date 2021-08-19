package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */
@Parcelize
data class BodyLogin(
    var userName: String,
    var password: String
    ): Parcelable {}