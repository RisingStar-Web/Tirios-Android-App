package com.ai.tirios.dataclasses

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Gaurav Kumar Tanwar on 28,May,2021
 * Noiad, U.P.
 * India
 */
@Parcelize
data class BodyUpdateMyProfile(
    var mobile: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var documentsUploaded: Boolean
):Parcelable

