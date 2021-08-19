package com.ai.tirios.ui.setting

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ai.tirios.R
import com.bumptech.glide.Glide

class SettingsBindingAdapter {

    companion object {

        @BindingAdapter("setProfilePhoto")
        @JvmStatic
        fun setProfilePhoto(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_myprofile)
                .circleCrop()
                .into(view)
        }

        @BindingAdapter("setSettingsIcon")
        @JvmStatic
        fun setSettingsIcon(view: ImageView, resource: Int?) {
            Glide.with(view.context)
                .load(resource)
//                .placeholder(R.drawable.ic_home)
                .circleCrop()
                .into(view)
        }
    }
}