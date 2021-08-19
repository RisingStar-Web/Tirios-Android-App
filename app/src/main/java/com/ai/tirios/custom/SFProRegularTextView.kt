package com.ai.tirios.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ai.tirios.utils.Utilities

/**
 * Created by Maruthi Ram Yadav on 08-05-2021.
 */

class SFProRegularTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        typeface = Utilities.getSFProRegular(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        typeface = Utilities.getSFProRegular(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        typeface = Utilities.getSFProRegular(context)
    }
}