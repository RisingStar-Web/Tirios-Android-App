package com.ai.tirios.ui.owned

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.R
import com.ai.tirios.databinding.ItemOwnedBinding
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.SharedStorage.SharedStorage

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */

class OwnedAdapter internal constructor(
    var context: Context,
    var propertiesList: MutableList<Property>,
    var sharedStorage: SharedStorage,
    var adapterItemClick: AdapterItemClick
) : RecyclerView.Adapter<OwnedAdapter.MyViewHolder>(){

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

        if (propertiesList.get(position).getLandlordActivationStatus().equals("Approved")
            || propertiesList.get(position).getLandlordActivationStatus().equals("Deactivated")){
                if (propertiesList.get(position).getLandlordActivationStatus().equals("Approved")){
                    holder.binding.tvRequestActivation.setText(context.getString(R.string.request_deactivation))
                }else{
                    holder.binding.tvRequestActivation.setText(context.getString(R.string.request_activation))
                }

            holder.binding.tvRequestActivation.alpha = 1.0f
            holder.binding.tvRequestActivation.isClickable = true
            holder.binding.tvRequestActivation.isEnabled = true
        }else{
            if (propertiesList.get(position).getLandlordActivationStatus().equals("Pending Deactivation")){
                holder.binding.tvRequestActivation.setText(context.getString(R.string.pending_deactivation))
            }else{
                holder.binding.tvRequestActivation.setText(context.getString(R.string.pending_activation))
            }

            holder.binding.tvRequestActivation.alpha = 0.3f
            holder.binding.tvRequestActivation.isClickable = false
            holder.binding.tvRequestActivation.isEnabled = false
        }
        holder.binding.root.setOnClickListener {
            adapterItemClick.onItemClick(position, propertiesList.get(position).Id, "false")
        }
        holder.binding.tvRequestActivation.setOnClickListener {
            adapterItemClick.onItemClick(position, propertiesList.get(position).Id, "RequestActivation")
        }
    }

}
