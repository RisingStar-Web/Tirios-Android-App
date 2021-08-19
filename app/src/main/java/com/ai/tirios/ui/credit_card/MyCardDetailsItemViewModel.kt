package com.ai.tirios.ui.credit_card

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ai.tirios.dataclasses.MyCardDetails

class MyCardDetailsItemViewModel: ViewModel() {
    var item = ObservableField<MyCardDetails>()
}