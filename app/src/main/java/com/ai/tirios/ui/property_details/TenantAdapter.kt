package com.ai.tirios.ui.property_details

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.R
import com.ai.tirios.custom.Symbols
import com.ai.tirios.databinding.ItemTenantBinding
import com.ai.tirios.dataclasses.Tenants
import com.ai.tirios.listeners.AdapterItemClick

/**
 * Created by Maruthi Ram Yadav on 18-05-2021.
 */

class TenantAdapter internal constructor(
    var context: Context,
    var arrayList: List<Tenants>,
    var listener: AdapterItemClick
)
    :RecyclerView.Adapter<TenantAdapter.MyViewHolder>() {

    public class MyViewHolder(itemTenantBinding: ItemTenantBinding):
        RecyclerView.ViewHolder(itemTenantBinding.root){
        var binding: ItemTenantBinding
        init {
            this.binding = itemTenantBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)
        var binding= ItemTenantBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tenant = arrayList.get(position)
        holder.binding.tvRentPerMonth.setCurrency(Symbols.USA)

        holder.binding.imgEdit.setOnClickListener {
            listener.onItemClick(position, arrayList.get(position).TenantId, "edit")
        }

        holder.binding.imgIsResiding.setOnClickListener {
            if (arrayList.get(position).IsResiding){
                AlertDialog.Builder(context).apply {
                    setMessage(context.resources.getString(R.string.are_you_sure_this_means_that_your_tenant_has))
                    setPositiveButton(context.resources.getString(R.string.yes)) { dialog, _ -> dialog.cancel()
                        listener.onItemClick(position, arrayList.get(position).TenantId, "is_residing")
                    }
                    setNegativeButton(context.resources.getString(R.string.no)){
                            dialog, _ -> dialog.cancel()
                    }
                    show()
                }
            }
        }

        holder.binding.llShare.setOnClickListener {
            listener.onItemClick(position, arrayList.get(position).TenantId, "share")
        }
    }

    override fun getItemCount(): Int = arrayList.size

}
