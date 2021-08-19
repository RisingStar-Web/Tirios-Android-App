package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 10-05-2021.
 */
@Parcelize
data class BodySignUp(
    var mobile:String,
    var firstName:String,
    var lastName:String,
    var password: String,
    var confirmPassword: String,
    var isTenant: Boolean
    ): Parcelable {}