package com.ai.tirios.ui.add_tenant

import com.ai.tirios.base.Medium

/**
 * Created by Maruthi Ram Yadav on 17-05-2021.
 */
interface AddTenantMidium: Medium {
    fun onBackArrowPressed()
    fun adFamilyMember()
    fun removeFamilyMember()
    fun datePicker(from: String)
    fun adTenant()
}