package com.ai.tirios.ui.add_properties

import com.ai.tirios.base.Medium

/**
 * Created by Maruthi Ram Yadav on 20-05-2021.
 */
interface AddPropertyMedium: Medium {
    fun onBackArrowPressed()
    fun addProperty()
    fun datePicker(from: String)
}