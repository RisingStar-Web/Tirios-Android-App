package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 20-05-2021.
 */
@Parcelize
data class BodyIsReciding(
    var tenantPropertyId: Int,
    var isResiding: Boolean
) : Parcelable{
}