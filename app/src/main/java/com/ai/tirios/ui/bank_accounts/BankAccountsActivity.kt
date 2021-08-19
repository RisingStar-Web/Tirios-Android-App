package com.ai.tirios.ui.bank_accounts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityBankAccountsBinding
import com.ai.tirios.ui.add_bank_accounts.AddBankAccountsActivity
import javax.inject.Inject

class BankAccountsActivity : BaseActivity<ActivityBankAccountsBinding, BankAccountsViewModel>() , BankAccountsMedium{

    var binding: ActivityBankAccountsBinding? = null
    var sharedStorage: SharedStorage? = null
    var bundle= Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = dataBinding
        binding!!.bankAccounts = viewModel
        viewModel.medium = this
        binding!!.activity = this
        binding!!.bankAccountsSize = -1
        sharedStorage = SharedStorage(this)

        var linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager

        viewModel.bankAccounts.observe(this, Observer {
            binding!!.bankAccountsSize = it.data.bankAccounts.size
            binding!!.rcView.adapter = BankAccountsAdapter(this, it.data.bankAccounts)
        })

        viewModel.landLordProperty.observe(this, Observer {
            if (sharedStorage!!.isLandlord()){
                for(j in 0 until it.OwnedProperty.size){
                    if (it.OwnedProperty.get(j).State != null && it.OwnedProperty.get(j).Street != null
                        && it.OwnedProperty.get(j).ZipCode != null && it.OwnedProperty.get(j).City != null){
                        bundle.clear()
                        bundle.putString("State", it.OwnedProperty.get(j).State)
                        bundle.putString("Street", it.OwnedProperty.get(j).Street)
                        bundle.putString("ZipCode", it.OwnedProperty.get(j).ZipCode)
                        bundle.putString("City", it.OwnedProperty.get(j).City)

                        viewModel.getBankAccounts(sharedStorage!!.getid()!!)
                        break
                    }
                }
            }else{
                for (i in 0 until it.TenantProperty.size){
                    for (j in 0 until it.TenantProperty.get(i).Tenants.size){
                        if (it.TenantProperty.get(i).Tenants.get(j).UserId.equals(sharedStorage!!.getid())){
                            bundle.clear()
                            bundle.putString("State", it.TenantProperty.get(i).State)
                            bundle.putString("Street", it.TenantProperty.get(i).Street)
                            bundle.putString("ZipCode", it.TenantProperty.get(i).ZipCode)
                            bundle.putString("City", it.TenantProperty.get(i).City)

                            viewModel.getBankAccounts(sharedStorage!!.getid()!!)
                            break
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProperties(sharedStorage!!.getid()!!)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_bank_accounts
    override lateinit var viewModel: BankAccountsViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun addAccountClick() {
        if (bundle != null)
            navigateTo(AddBankAccountsActivity::class.java, false, bundle)
    }

}
