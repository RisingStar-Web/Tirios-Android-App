package com.ai.tirios.ui.bank_accounts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemBankAccountBinding
import com.ai.tirios.dataclasses.BankAccount

/**
 * Created by Maruthi Ram Yadav on 02-06-2021.
 */

class BankAccountsAdapter internal constructor(var context: Context, var arrayList: List<BankAccount>)
    : RecyclerView.Adapter<BankAccountsAdapter.MyViewHolder>() {

    public class MyViewHolder(itemBankAccountBinding: ItemBankAccountBinding):
        RecyclerView.ViewHolder(itemBankAccountBinding.root){
        var binding: ItemBankAccountBinding
        init {
            this.binding = itemBankAccountBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)
        var binding= ItemBankAccountBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.account = arrayList.get(position)
    }

    override fun getItemCount(): Int = arrayList.size

}