package com.ai.tirios.dataclasses

import android.os.Parcelable
import com.ai.tirios.utils.Utilities
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class Tenants(
    val DueDate: String,
    val Email: String,
    val FirstName: String,
    val IsActive: Boolean,
    val IsInvited: Boolean,
    var IsResiding: Boolean,
    val LastName: String,
    val Mobile: String,
    val NumberOfFamilyMembers: Int,
    val PaymentFrequency: Int,
    val Rent: Double,
    val RentDeposit: Double,
    val RentInvoiceId: Int,
    val SecondDueDate: String,
    val Status: Int,
    val TenantDocuments: List<ResponseTenantImageUpload>,
    val TenantId: Int,
    val TenantStatus: String,
    val UserId: String,
    val UserName: String
):Parcelable{

    fun getNormalDueDate(): String{
        if (DueDate != null)
            return Utilities.convertUtcToNormalTenant(DueDate)
        else
            return ""
    }

    fun getNormalDueDateWithMonthName(): String{
        if (DueDate != null)
            return Utilities.convertUtcToNormalTenant(DueDate)
        else
            return ""
    }

    fun getNormalSecondDueDate(): String{
        if (SecondDueDate != null)
            return Utilities.convertUtcToNormal(SecondDueDate)
        else
            return ""
    }

    fun getNormalSecondDueDateWithMonthName(): String{
        if (SecondDueDate != null)
            return Utilities.convertUtcToDateWithMonthNameAndYear(SecondDueDate)
        else
            return ""
    }

    fun getMobileNumber():String{
        if (Mobile != null)
            return Mobile.substring(Mobile.length - 10, Mobile.length)
        else
            return ""
    }
    fun getCountryCode():String{
        var codelength = Mobile.length - 10
        return Mobile.substring(0, codelength)
    }

    fun getTenantsRentInDouble(): String{
        return "$" + Utilities.indianPriceFormat(Rent).toString()
    }

    fun getTenantsRentDepositInDouble(): String{
        return Utilities.indianPriceFormat(RentDeposit).toString()
    // String.format("%.2f", RentDeposit)
    }

}
