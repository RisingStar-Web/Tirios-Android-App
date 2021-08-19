package com.ai.tirios.dataclasses

import android.text.TextUtils
import com.ai.tirios.R
import com.ai.tirios.utils.Utilities

data class ResponseMaintainance(
    val Description: String,
    val Documents: List<Documents>,
    val FirstPreferredDate: String,
    val Id: Int,
    val Invoice: Invoice,
    val InvoiceStatus: String,
    val JobStatusName: String,
    val PreferredDate: String,
    val PropertyId: Int,
    val ScheduleDate: String,
    val SecondPreferredDate: String,
    val SerivceProviderMobile: String,
    val ServiceCategory: String,
    val ServiceCategoryName: String,
    val ServiceProvider: ServiceProvider,
    val ServiceProviderId: Int,
    val ServiceProviderName: String,
    val ServiceType: String,
    val Status: Int,
    val TenantCity: String,
    val TenantProperty: TenantProperty,
    val TenantPropertyId: Int,
    val TenantState: String,
    val TenantStreet: String,
    val TenantZipCode: String,
    val Total: String,
    val VideoMeetLink: String
){
    fun getServiceStatus(): String{
        if (Status == 1){
            return "Open"
        }else if (Status == 2){
            return "Active"
        }else if (Status == 3){
            return "Closed"
        }else if (Status == 4){
            return "Rescheduled"
        }else if (Status == 5){
            return "Cancelled"
        }else{
            return ""
        }
    }

    fun getServiceProviderNameFromId(): String{
        if (TextUtils.equals(ServiceCategory,"1")){
            return "Microwave"
        }else if (TextUtils.equals(ServiceCategory,"2")){
            return "Refrigerator"
        }else if (TextUtils.equals(ServiceCategory,"3")){
            return "Cooktop"
        }else if (TextUtils.equals(ServiceCategory,"4")){
            return "RangehoodVent"
        }else if (TextUtils.equals(ServiceCategory,"5")){
            return "Dishwasher"
        }else if (TextUtils.equals(ServiceCategory,"6")){
            return "FireAlarm"
        }else if (TextUtils.equals(ServiceCategory,"7")){
            return "DoorLock"
        }else if (TextUtils.equals(ServiceCategory,"8")){
            return "BathroomShower"
        }else if (TextUtils.equals(ServiceCategory,"9")){
            return "BathroomSink"
        }else if (TextUtils.equals(ServiceCategory,"10")){
            return "BathroomToilet"
        }else if (TextUtils.equals(ServiceCategory,"11")){
            return "Other"
        }else{
            return ""
        }
    }

    fun getServiceStatusColor(): Int{
        if (Status == 1){
            return R.color.orange
        }else if (Status == 2){
            return R.color.green
        }else if (Status == 3){
            return R.color.red
        }else if (Status == 4){
            return R.color.semi_black
        }else if (Status == 5){
            return R.color.red
        }else{
            return R.color.gray
        }
    }

    fun getServiceTypeImage(): Int{
        if(ServiceType != null){
            if (ServiceType.equals("Home_Service")){
                return R.drawable.ic_maintenance_home
            }else {
                return R.drawable.ic_call
            }
        }else{
            return R.drawable.ic_maintenance_home
        }
    }

    fun getFirstPreferredNormalDate(): String{
        if (FirstPreferredDate != null)
            return Utilities.convertUtcToDateWithMonthName(FirstPreferredDate)
        else
            return ""
    }

    fun getSecondPreferredNormalDate(): String{
        if (SecondPreferredDate != null)
            return Utilities.convertUtcToDateWithMonthName(SecondPreferredDate)
        else
            return ""
    }

    fun getLocalFirstPreferredNormalDate(): String{
        if (FirstPreferredDate != null)
            return Utilities.getLocalDateFromUtc(FirstPreferredDate)
        else
            return ""
    }

    fun getLocalSecondPreferredNormalDate(): String{
        if (SecondPreferredDate != null)
            return Utilities.getLocalDateFromUtc(SecondPreferredDate)
        else
            return ""
    }

    fun dateToShow(): String{
        if (PreferredDate != null){
            return Utilities.getLocalDateFromUtc(PreferredDate)
        }else if (ScheduleDate != null){
            return Utilities.getLocalDateFromUtc(ScheduleDate)
        }else if (FirstPreferredDate != null){
            return Utilities.getLocalDateFromUtc(FirstPreferredDate)
        }else if (SecondPreferredDate != null){
            return Utilities.getLocalDateFromUtc(SecondPreferredDate)
        }else{
            return ""
        }
    }

}
