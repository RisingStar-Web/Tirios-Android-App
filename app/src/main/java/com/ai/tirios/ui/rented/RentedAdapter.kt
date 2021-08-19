package com.ai.tirios.ui.rented

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemOwnedBinding
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.SharedStorage.SharedStorage

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */

class RentedAdapter internal constructor(
    var context: Context,
    var propertiesList: MutableList<Property>,
    var sharedStorage: SharedStorage,
    var adapterItemClick: AdapterItemClick
) : RecyclerView.Adapter<RentedAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)
        var binding= ItemOwnedBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount()= propertiesList.size

    public class MyViewHolder(itemOwnedBinding: ItemOwnedBinding):RecyclerView.ViewHolder(itemOwnedBinding.root){
        var binding: ItemOwnedBinding
        init {
            this.binding =itemOwnedBinding
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.itemOwned = propertiesList.get(position)
        holder.binding.isLandlord = sharedStorage.isLandlord()
        holder.binding.root.setOnClickListener {
            adapterItemClick.onItemClick(position, propertiesList.get(position).Id, "false")
        }
    }

}
