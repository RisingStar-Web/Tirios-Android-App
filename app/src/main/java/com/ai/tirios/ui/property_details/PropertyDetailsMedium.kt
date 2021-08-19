package com.ai.tirios.ui.property_details

import com.ai.tirios.base.Medium

/**
 * Created by Maruthi Ram Yadav on 15-05-2021.
 */
interface PropertyDetailsMedium: Medium {
    fun addTenant()
    fun onBackArrowPressed()
    fun editProperty()
}