package com.ai.tirios.ui.add_tenant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.databinding.ItemPropertyGalleryBinding
import com.ai.tirios.dataclasses.Documents
import com.ai.tirios.dataclasses.ResponseTenantImageUpload

/**
 * Created by Maruthi Ram Yadav on 18-05-2021.
 */

class TenantAgreementAdapter internal constructor(var context: Context, var arrayList: List<ResponseTenantImageUpload>)
    :RecyclerView.Adapter<TenantAgreementAdapter.MyViewHolder>() {

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
        holder.binding.tenantDocuments = arrayList.get(position)
    }

    override fun getItemCount(): Int = arrayList.size

}