package com.ai.tirios.listeners

import android.os.Bundle

/**
 * Created by Maruthi Ram Yadav on 15-05-2021.
 */
interface AdapterItemClickForBundle {
    fun onItemClick(position: Int, bundle: Bundle, type: String)
}