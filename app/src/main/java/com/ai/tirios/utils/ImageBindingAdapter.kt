package com.ai.tirios.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ai.tirios.R
import com.bumptech.glide.Glide
import java.net.URI


object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:loadimage")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("bind:loadimageDrawable")
    fun setImageDrawable(imageView: ImageView, drawable: Drawable) {
        imageView.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("bind:loadimage")
    fun setImageGlide(imageView: ImageView, url: String?){
        if (url != null && !url.isEmpty()) {
            Glide
                .with(imageView.context)
                .load(url)
                .placeholder(R.drawable.ic_photo_placeholder)
                .into(imageView)
        }else{
            imageView.setImageResource(R.drawable.ic_photo_placeholder)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:loadimage")
    fun setImageUri(imageView: ImageView, uri: Uri){
        if (uri != null){
            imageView.setImageURI(uri)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:loadimageBitmap")
    fun setImageBitmap(imageView: ImageView, bitmap: Bitmap){
        if (bitmap != null){
            imageView.setImageBitmap(bitmap)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:applyColor")
    fun setApplyColor(textView: TextView, color: Int) {
        if(color != null){
            textView.setTextColor(color)
        }
    }
}
