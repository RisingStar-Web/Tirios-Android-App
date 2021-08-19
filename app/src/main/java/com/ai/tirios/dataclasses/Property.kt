package com.ai.tirios.dataclasses

import android.os.Parcelable
import com.ai.tirios.utils.Utilities
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@Parcelize
data class Property(
    var City: String,
    var CoreLogicId: String,
    var CorelogicStatus: Int,
    var CorelogicStatusValue: String,
    var DocumentsUpdated: Boolean,
    var Id: Int,
    var LandlordEmail: String,
    var LandlordFirstName: String,
    var LandlordId: String,
    var LandlordLastName: String,
    var LandlordMobile: String,
    var LandlordStatus: Int,
    var LandlordStatusValue: String,
    var MortgageAmount: Double,
    var MortgageDueDate: String,
    var MortgageNumber: String,
    var Name: String,
    var PropertyTaxAmount: Double,
    var PropertyTaxDueDate: String,
    var PropertyTaxNumber: String,
    var Rent: Int,
    var State: String,
    var Status: Int,
    var Street: String,
    var Subscription: Int,
    var SubscriptionEndDate: String,
    var SubscriptionName: String,
    var SubscriptionStartDate: String,
    var SubscriptionType: Int,
    var Tenants: MutableList<Tenants>,
    var Documents: List<Documents>,
    var ZipCode: String
): Parcelable{
    fun getStatusName(): String{
        if (Status.equals(1)){
            return "Pending"
        }else if (Status.equals(2)){
            return "Approved"
        }else if (Status.equals(3)){
            return "Rejected"
        }else if (Status.equals(4)){
            return "Pending Deactivation"
        }else {
            return "Deactivated"
        }
    }

    fun getLandlordActivationStatus(): String{
        if (LandlordStatus.equals(1)){
            return "Pending"
        }else if (LandlordStatus.equals(2)){
            return "Approved"
        }else if (LandlordStatus.equals(3)){
            return "Rejected"
        }else if (LandlordStatus.equals(4)){
            return "Pending Deactivation"
        }else if (LandlordStatus.equals(5)){
            return "Deactivated"
        }else{
            return ""
        }
    }

    fun getNormalMortgageDueDate(): String{
        if (MortgageDueDate != null)
            return Utilities.convertUtcToNormal(MortgageDueDate)
        else
            return ""
    }
    fun getNormalPropertyTaxDueDate(): String{
        if (PropertyTaxDueDate != null)
            return Utilities.convertUtcToNormal(PropertyTaxDueDate)
        else
            return ""
    }
    fun getAddress(): String{
        if(Street != null)
            return Street+' '+City+' '+State+' '+ZipCode
        else
            return ""
    }

    override fun toString(): String {
        return getAddress()
    }

    fun getPropertyTaxAmountInDouble(): String{
        return "$ " + String.format("%.2f", PropertyTaxAmount)
    }

    fun getPropertyTaxAmountInDoubleNotDollar(): String{
        return Utilities.indianPriceFormat(PropertyTaxAmount).toString()
//        return String.format("%.2f", PropertyTaxAmount)
    }

    fun getMortgageAmountInDouble(): String{
        return "$ " + String.format("%.2f", MortgageAmount)
    }

    fun getMortgageAmountInDoubleNotDollar(): String{
        return Utilities.indianPriceFormat(MortgageAmount).toString()
//        return String.format("%.2f", MortgageAmount)
    }
}
