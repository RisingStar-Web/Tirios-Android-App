package com.ai.tirios.ui.credit_card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemMyCardDetailsBinding
import com.ai.tirios.dataclasses.BankAccount

/**
 * Created by Maruthi Ram Yadav on 02-06-2021.
 */

class CardAdapter internal constructor(var context: Context, var arrayList: List<BankAccount>)
    : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    public class MyViewHolder(itemMyCardDetailsBinding: ItemMyCardDetailsBinding):
        RecyclerView.ViewHolder(itemMyCardDetailsBinding.root){
        var binding: ItemMyCardDetailsBinding
        init {
            this.binding = itemMyCardDetailsBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)
        var binding= ItemMyCardDetailsBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.card = arrayList.get(position)
    }

    override fun getItemCount(): Int = arrayList.size

}