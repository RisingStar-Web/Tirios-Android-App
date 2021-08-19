package com.ai.tirios.ui.credit_card.add_card

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityAddCardBinding
import com.ai.tirios.dataclasses.BodyAddCreditCard
import com.ai.tirios.dataclasses.PaymentX
import javax.inject.Inject


class AddCardActivity : BaseActivity<ActivityAddCardBinding, AddCardViewModel>(), AddCardMedium {

    private lateinit var binding: ActivityAddCardBinding
    var sharedStorage: SharedStorage? = null
    var customerId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding!!
        viewModel.medium = this
        binding.addCard = viewModel
        sharedStorage = SharedStorage(this)

        viewModel.getBankAccounts(sharedStorage!!.getid()!!)

        viewModel.bankAccounts.observe(this, Observer {
            for (i in 0 until it.data.bankAccounts.size){
                if (it.data.bankAccounts.get(i).customerId != null && it.data.bankAccounts.get(i).method > 0){
                    customerId = it.data.bankAccounts.get(i).customerId
                    break
                }
            }
        })

        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            private var processingFlag = false
            private var SEPARATOR = ' '
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (processingFlag) {
                    return
                }
                processingFlag = true
                try {
                    var firstFour = ""
                    var secondFour = ""
                    var thirdFour = ""
                    var fourthFour = ""
                    binding.cardOne.text = resources.getString(R.string.x_x_x_x)
                    for (i in s.indices) {
                        when (i) {
                            0, 1, 2, 3 -> {
                                firstFour += if (i != 3) {
                                    s[i].toString() + " "
                                } else {
                                    s[i].toString()
                                }
                                binding.cardOne.text = firstFour
                                binding.cardTwo.text = resources.getString(R.string.x_x_x_x)
                            }
                            4, 5, 6, 7 -> {
                                secondFour += if (i != 7) {
                                    s[i].toString() + " "
                                } else {
                                    s[i].toString()
                                }
                                binding.cardTwo.text = secondFour
                                binding.cardThree.text = resources.getString(R.string.x_x_x_x)
                                binding.cardFour.text = resources.getString(R.string.x_x_x_x)
                            }
                            8, 9, 10, 11 -> {
                                thirdFour += if (i != 11) {
                                    s[i].toString() + " "
                                } else {
                                    s[i].toString()
                                }
                                binding.cardThree.text = thirdFour
                                binding.cardFour.text = resources.getString(R.string.x_x_x_x)
                            }
                            12, 13, 14, 15 -> {
                                fourthFour += if (i != 15) {
                                    s[i].toString() + " "
                                } else {
                                    s[i].toString()
                                }
                                binding.cardFour.text = fourthFour
                            }
                        }
                    }
                } finally {
                    processingFlag = false
                }
            }

        })

        binding.etCardHolderName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) {
                    binding.cardHolderName.text = resources.getString(R.string.name_surname)
                } else {
                    binding.cardHolderName.text = s.toString()
                }
            }
        })

        binding.etCardValidDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty() && (s.length % 3 == 0)) {
                    val c: Char = s[s.length - 1]
                    if ('/' == c) {
                        s.delete(s.length - 1, s.length)
                    }
                }
                if (s.isNotEmpty() && (s.length % 3 == 0)) {
                    val c: Char = s[s.length - 1]
                    if (Character.isDigit(c) && TextUtils.split(
                            s.toString(),
                            "/"
                        ).size <= 2
                    ) {
                        s.insert(s.length - 1, "/")
                    }
                }

                if (s.toString().isEmpty()) {
                    binding.validDate.text = resources.getString(R.string.m_m_y_y)
                } else {
                    binding.validDate.text = s.toString()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_add_card
    override lateinit var viewModel: AddCardViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all
    override fun onBackArrowPressed() {
        finish()
    }

    override fun addCreditCard() {
        if (TextUtils.isEmpty(binding.etCardNumber.text.toString())) {
            showAlert(this.resources.getString(R.string.please_enter_card_number))
        } else if (TextUtils.isEmpty(binding.etCardHolderName.text.toString())) {
            showAlert(this.resources.getString(R.string.please_enter_card_holder_name))
        } else if (TextUtils.isEmpty(binding.etCardValidDate.text.toString())) {
            showAlert(this.resources.getString(R.string.please_enter_card_validity_date))
        }else if (TextUtils.isEmpty(binding.etCardCVV.text.toString())) {
            showAlert(this.resources.getString(R.string.please_enter_the_security_code))
        } else if (binding.etCardCVV.text.toString().length != 3) {
            showAlert(this.resources.getString(R.string.security_code_is_invalid))
        } else if (!binding.checkBox.isChecked) {
            showAlert(this.resources.getString(R.string.please_check_the_terms_and_conditions))
        } else {
            var bodyAddCreditCard = BodyAddCreditCard(customerId,
                binding.etCardValidDate.text.toString().replace("/",""),
                PaymentX(binding.etCardCVV.text.toString(),
                    binding.etCardNumber.text.toString())
            )
            viewModel.addCardAPI(bodyAddCreditCard)
        }
    }
}