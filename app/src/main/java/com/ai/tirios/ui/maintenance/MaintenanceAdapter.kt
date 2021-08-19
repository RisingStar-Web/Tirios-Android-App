package com.ai.tirios.ui.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemMaintenanceBinding
import com.ai.tirios.dataclasses.ResponseMaintainance
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.listeners.AdapterItemClickForBundle

/**
 * Created by Maruthi Ram Yadav on 18-05-2021.
 */

class MaintenanceAdapter internal constructor(
    var context: MaintenanceFragment,
    var arrayList: List<ResponseMaintainance>,
    var listener: AdapterItemClickForBundle
) : RecyclerView.Adapter<MaintenanceAdapter.MyViewHolder>() {

    public class MyViewHolder(itemMaintenanceBinding: ItemMaintenanceBinding) :
        RecyclerView.ViewHolder(itemMaintenanceBinding.root) {
        var binding: ItemMaintenanceBinding

        init {
            this.binding = itemMaintenanceBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = ItemMaintenanceBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.context = context
        holder.binding.maintenance = arrayList.get(position)
        var isLockItemView = false
        var status = arrayList[position].getServiceStatus()
        if (status.equals("Closed", true))
            holder.binding.tvViewDetails.visibility = View.VISIBLE

        if (status.equals("Active", true) || status.equals("Open", true))
            holder.binding.tvViewDetails.visibility = View.GONE

        if (arrayList[position].Invoice != null) {
            var id = arrayList[position].Invoice.Id
            var invoiceStatus = arrayList[position].Invoice.PaymentStatusValue

            if (invoiceStatus.equals("Paid", true) && id > 0 && id != null) {
                holder.binding.tvViewDetails.visibility = View.VISIBLE
                holder.binding.tvViewReceipt.visibility = View.VISIBLE
                holder.binding.tvViewDetails.text = "Paid"
                isLockItemView = true
            }
        }
        holder.binding.root.setOnClickListener {
//            if (!isLockItemView){
                var bundle = Bundle()
                bundle.putInt("maintenance_request_id", arrayList[position].Id)
                listener.onItemClick(position, bundle, "")
//            }
        }
        holder.binding.tvViewDetails.setOnClickListener {
            if (holder.binding.tvViewDetails.text.equals("View Invoice")) {
                var bundle = Bundle()
                bundle.putInt("maintenance_request_id", arrayList[position].ServiceProviderId)
                bundle.putString("PayrixMerchantId", arrayList[position].ServiceProvider.PayrixMerchantId)
                listener.onItemClick(position, bundle, "view")
            }
        }
        holder.binding.tvViewReceipt.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("maintenance_request_id", arrayList[position].ServiceProviderId)
            bundle.putString("PayrixMerchantId", arrayList[position].ServiceProvider.PayrixMerchantId)
            listener.onItemClick(position, bundle, "view")
        }

    }

    override fun getItemCount(): Int = arrayList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

}
