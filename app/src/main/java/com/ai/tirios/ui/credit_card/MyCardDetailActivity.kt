package com.ai.tirios.ui.credit_card

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityMyCardDetailsBinding
import com.ai.tirios.ui.credit_card.add_card.AddCardActivity
import com.ai.tirios.dataclasses.BankAccount
import com.ai.tirios.ui.bank_accounts.BankAccountsAdapter
import java.util.Observer
import javax.inject.Inject

class MyCardDetailsActivity : BaseActivity<ActivityMyCardDetailsBinding, MyCardDetailsViewModel>(), MyCardDetailsMedium {

    private lateinit var binding: ActivityMyCardDetailsBinding
    private lateinit var myCardDetailsItemViewModel: MyCardDetailsItemViewModel
    var sharedStorage: SharedStorage? = null
    val bankAccounts: MutableList<BankAccount> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding!!
        viewModel.medium = this
        binding.myCard = viewModel
        sharedStorage = SharedStorage(this)
        var linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.layoutManager = linearLayoutManager

        viewModel.bankAccounts.observe(this, androidx.lifecycle.Observer {
            bankAccounts.clear()
            for (i in 0 until it.data.bankAccounts.size){
                if (it.data.bankAccounts.get(i).method == 0){
                    bankAccounts.add(it.data.bankAccounts.get(i))
                }
            }
            binding.recyclerView.adapter = CardAdapter(this, bankAccounts)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBankAccounts(sharedStorage!!.getid()!!)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_my_card_details
    override lateinit var viewModel: MyCardDetailsViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onBackArrowPressed() {
        finish()
    }

    override fun addAddCard() {
        navigateTo(AddCardActivity::class.java)
    }
}
