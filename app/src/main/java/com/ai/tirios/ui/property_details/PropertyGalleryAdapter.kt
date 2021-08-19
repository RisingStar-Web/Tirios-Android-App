package com.ai.tirios.ui.property_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemPropertyGalleryBinding
import com.ai.tirios.dataclasses.Documents
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.listeners.AdapterItemClickForBundle

/**
 * Created by Maruthi Ram Yadav on 18-05-2021.
 */

class PropertyGalleryAdapter internal constructor(var context: Context,
                                                  var arrayList: List<Documents>,
                                                  var listener : AdapterItemClickForBundle)
    :RecyclerView.Adapter<PropertyGalleryAdapter.MyViewHolder>() {

    public class MyViewHolder(itemPropertyGalleryBinding: ItemPropertyGalleryBinding):
        RecyclerView.ViewHolder(itemPropertyGalleryBinding.root){
        var binding: ItemPropertyGalleryBinding
        init {
            this.binding = itemPropertyGalleryBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)
        var binding= ItemPropertyGalleryBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.propertyGallery = arrayList.get(position)
        holder.binding.image.setOnClickListener {
            var bundle= Bundle()
            bundle.putString("image_url", arrayList.get(position).DocumentURL)
            listener.onItemClick(position, bundle, "")
        }
    }

    override fun getItemCount(): Int = arrayList.size

}