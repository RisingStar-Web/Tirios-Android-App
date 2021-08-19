package com.ai.tirios.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemSettingsBinding

class SettingsAdapter internal constructor(private val settingsList: ArrayList<Settings>, private val callBack: (Settings) -> Unit) :
    RecyclerView.Adapter<SettingsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSettingsBinding.inflate(layoutInflater, parent, false)
        binding.viewModel = SettingsItemViewModel()
        binding.rootView.setOnClickListener {
            binding.viewModel?.item?.get()?.let {
                callBack.invoke(it)
            }
        }
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(settingsList[position])
    }

    override fun getItemCount(): Int = settingsList.size

    class MyViewHolder(private val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(settings: Settings) {
            binding.viewModel?.item?.set(settings)
            binding.executePendingBindings()
        }
    }
}