package com.ai.tirios.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 20-05-2021.
 */
@Parcelize
data class LandLordProperty(
    var OwnedProperty: MutableList<Property>,
    var TenantProperty: MutableList<Property>
):Parcelable {
}