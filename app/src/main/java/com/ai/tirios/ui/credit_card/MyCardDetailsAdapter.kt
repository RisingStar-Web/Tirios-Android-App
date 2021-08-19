package com.ai.tirios.ui.credit_card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemMyCardDetailsBinding
import com.ai.tirios.dataclasses.MyCardDetails

class MyCardDetailsAdapter internal constructor(
    private val savedCardsList: ArrayList<MyCardDetails>,
    private val callBack: (MyCardDetails) -> Unit
) :
    RecyclerView.Adapter<MyCardDetailsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMyCardDetailsBinding.inflate(layoutInflater, parent, false)
        binding.savedCardViewModel = MyCardDetailsItemViewModel()
        binding.rootView.setOnClickListener {
            binding.savedCardViewModel?.item?.get()?.let {
                callBack.invoke(it)
            }
        }
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(savedCardsList[position])
    }

    override fun getItemCount(): Int {
        return savedCardsList.size
    }

    class MyViewHolder(private val binding: ItemMyCardDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myCardDetails: MyCardDetails) {
            binding.savedCardViewModel?.item?.set(myCardDetails)
            binding.executePendingBindings()
        }
    }
}
