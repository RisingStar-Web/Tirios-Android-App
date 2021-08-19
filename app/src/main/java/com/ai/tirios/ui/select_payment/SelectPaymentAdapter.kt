package com.ai.tirios.ui.select_payment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemOwnedBinding
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.databinding.ItemSelectPaymentBinding
import com.ai.tirios.dataclasses.BankAccount

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */

class SelectPaymentAdapter internal constructor(
    var context: Context,
    var bankAccounts: MutableList<BankAccount>,
    var adapterItemClick: AdapterItemClick
) : RecyclerView.Adapter<SelectPaymentAdapter.MyViewHolder>(){

    var SelectedAccountPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)
        var binding= ItemSelectPaymentBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount()= bankAccounts.size

    public class MyViewHolder(itemSelectPaymentBinding: ItemSelectPaymentBinding):RecyclerView.ViewHolder(itemSelectPaymentBinding.root){
        var binding: ItemSelectPaymentBinding
        init {
            this.binding = itemSelectPaymentBinding
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.bank = bankAccounts.get(position)

        if (position == SelectedAccountPosition)
            holder.binding.rb.isChecked = true
        else
            holder.binding.rb.isChecked = false

        holder.binding.rb.setOnClickListener {
            SelectedAccountPosition = position
            adapterItemClick.onItemClick(position, 0, "false")
            notifyDataSetChanged()
        }

    }

}
