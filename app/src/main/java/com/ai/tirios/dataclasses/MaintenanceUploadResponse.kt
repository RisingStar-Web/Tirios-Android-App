package com.ai.tirios.dataclasses

data class MaintenanceUploadResponse(
    val CreatedBy: Any,
    val CreatedOn: String,
    val Description: Any,
    val Documents: List<Document>,
    val FirstPreferredDate: Any,
    val Id: Int,
    val Invoice: Any,
    val IsActive: Boolean,
    val ModifiedBy: Any,
    val ModifiedOn: Any,
    val PreferredDate: Any,
    val ScheduleDate: Any,
    val SecondPreferredDate: Any,
    val SerivceProviderMobile: Any,
    val ServiceCategory: Any,
    val ServiceDetails: List<Any>,
    val ServiceProvider: Any,
    val ServiceProviderId: Any,
    val ServiceProviderName: Any,
    val ServiceType: Any,
    val Status: Int,
    val TenantProperty: TenantProperty,
    val TenantPropertyId: Int,
    val VideoMeetLink: Any
) {
    data class Document(
        val CreatedBy: Any,
        val CreatedOn: String,
        val DocumentExtension: String,
        val DocumentLocation: String,
        val DocumentName: String,
        val DocumentType: Int,
        val DocumentURL: String,
        val Id: Int,
        val IsActive: Boolean,
        val MaintenanceRequestId: Int,
        val ModifiedBy: Any,
        val ModifiedOn: Any
    )
}