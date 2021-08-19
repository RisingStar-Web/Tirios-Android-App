package com.ai.tirios.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.R
import com.ai.tirios.databinding.ItemNotificationsBinding
import com.ai.tirios.databinding.ItemOwnedBinding
import com.ai.tirios.dataclasses.ResponseNotifications
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.utils.Utilities


class NotificationAdapter internal constructor
    (var context: Context,
     private val notificationsList: MutableList<ResponseNotifications>,
     var listener: AdapterItemClick
)
    : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificationsBinding.
        inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.itemNotification = notificationsList.get(position).aps.alert
        holder.binding.notificationTime.text = Utilities.convertUtcToDateNotification(notificationsList.get(position).aps.alert.dateTime)
        holder.binding.imageView2.setOnClickListener {
            holder.binding.imageView2.setImageResource(R.drawable.rect_light_pink)
            listener.onItemClick(position,notificationsList.get(position).aps.alert.notificaionId,"click")
        }

    }

    override fun getItemCount(): Int = notificationsList.size


    class MyViewHolder(itemNotifiactionbinding: ItemNotificationsBinding) :
        RecyclerView.ViewHolder(itemNotifiactionbinding.root) {
        var binding: ItemNotificationsBinding
        init {
            this.binding = itemNotifiactionbinding
        }
    }


}
