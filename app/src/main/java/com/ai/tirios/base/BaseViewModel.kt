package com.ai.tirios.base

import android.view.View
import androidx.lifecycle.ViewModel
import com.ai.tirios.data.IDataManager
import java.lang.ref.WeakReference

abstract class BaseViewModel<M>(protected val dataManager: IDataManager) : ViewModel() {

    var listener: ((view: View) -> Unit)? = null

    var tittle: String = ""

    private var mMedium: WeakReference<M>? = null

    var medium: M
        get() = mMedium!!.get()!!
        set(medium) {
            this.mMedium = WeakReference(medium)
        }

}