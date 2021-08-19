package com.ai.tirios.ui.error_message

import com.ai.tirios.data.DataManager
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 11-05-2021.
 */
class ErrorMessageViewModel internal constructor(dataManager: DataManager):
    BaseViewModel<ErrorMessageMedium>(dataManager) {

        fun logout() = medium.logout()
}