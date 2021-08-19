package com.ai.tirios.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.R
import com.ai.tirios.custom.SFProRegularTextView
import com.ai.tirios.dataclasses.Message
import com.bumptech.glide.Glide


class MaintenanceBotAdapter(mContext: Context, chatList: ArrayList<Message>) :
    RecyclerView.Adapter<MaintenanceBotAdapter.MyViewHolder>() {
    private val FADE_DURATION = 2000 //FADE_DURATION in milliseconds

    private var con = mContext
    private var chat = chatList
    private var onChatClick: OnChatClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chat, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chat.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ll_row: LinearLayout
        var ll_tirios: LinearLayout
        var ll_self: LinearLayout
        var ll_btnTirios: LinearLayout
        var tv_msgtirios: SFProRegularTextView
        var tv_msgSelf: SFProRegularTextView
        var iv_tiriosMsgPic: ImageView
        var iv_msgPic: ImageView
        var iv_videoPlay: ImageView
        var vv_tiriosMsgPic: VideoView
        var vv_msgPic: VideoView
        var btn_tirios1: Button
        var btn_tirios2: Button

        init {
            ll_row = view.findViewById(R.id.ll_row) as LinearLayout
            ll_tirios = view.findViewById(R.id.ll_tirios) as LinearLayout
            ll_self = view.findViewById(R.id.ll_self) as LinearLayout
            ll_btnTirios = view.findViewById(R.id.ll_btnTirios) as LinearLayout
            tv_msgtirios = view.findViewById(R.id.tv_msgtirios) as SFProRegularTextView
            tv_msgSelf = view.findViewById(R.id.tv_msgSelf) as SFProRegularTextView
            iv_tiriosMsgPic = view.findViewById(R.id.iv_tiriosMsgPic) as ImageView
            iv_msgPic = view.findViewById(R.id.iv_msgPic) as ImageView
            iv_videoPlay = view.findViewById(R.id.iv_videoPlay) as ImageView
            vv_tiriosMsgPic = view.findViewById(R.id.vv_tiriosMsgPic) as VideoView
            vv_msgPic = view.findViewById(R.id.vv_msgPic) as VideoView
            btn_tirios1 = view.findViewById(R.id.btn_tirios1) as Button
            btn_tirios2 = view.findViewById(R.id.btn_tirios2) as Button
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (chat.get(position).id.equals("1")) {
            holder.ll_self.visibility = View.VISIBLE
            holder.ll_tirios.visibility = View.GONE
            if (chat.get(position).type == Message.Type.TEXT) {
                holder.tv_msgSelf.text = chat.get(position).message
                holder.tv_msgSelf.visibility = View.VISIBLE
            }
            if (chat.get(position).type == Message.Type.TIME) {
                holder.tv_msgSelf.text = chat.get(position).url
                holder.tv_msgSelf.visibility = View.VISIBLE
            }
            if (chat.get(position).type == Message.Type.IMAGE) {
//                val url = URL(chat.get(position).url)
//                holder.iv_tiriosMsgPic.setImageBitmap(chat.get(position).url)
                println("bitmap=====>"+chat.get(position).url)
                Glide.with(con).load(chat.get(position).imageBitMap).into(holder.iv_msgPic)
                holder.iv_msgPic.visibility = View.VISIBLE
            }
            if (chat.get(position).type == Message.Type.VIDEO) {
//                val url = URL(chat.get(position).url)
                Glide.with(con).load(chat.get(position).uri).into(holder.iv_msgPic)
//                holder.vv_msgPic.setVideoURI(chat.get(position).uri)
                holder.iv_videoPlay.visibility = View.VISIBLE
                holder.iv_msgPic.visibility = View.VISIBLE
            }
        } else {
            holder.ll_tirios.visibility = View.VISIBLE
            holder.ll_self.visibility = View.GONE
            if (chat.get(position).type == Message.Type.TEXT) {
                holder.tv_msgtirios.text = chat.get(position).message
                holder.tv_msgtirios.visibility = View.VISIBLE
            }
            if (chat.get(position).type == Message.Type.OPTIONS) {
                holder.ll_btnTirios.visibility = View.VISIBLE
                holder.tv_msgtirios.visibility = View.VISIBLE
                holder.tv_msgtirios.text = chat.get(position).message
                holder.btn_tirios1.text = chat.get(position).btnOption1
                holder.btn_tirios2.text = chat.get(position).btnOption2
                if (chat.get(position).btnOption2.isNullOrEmpty())
                    holder.btn_tirios2.visibility = View.GONE
            }
        }

        holder.iv_tiriosMsgPic.setOnClickListener {
                onChatClick?.onImageClick(position)
        }

        holder.iv_videoPlay.setOnClickListener {
            onChatClick?.onVideoClick(position)
        }

        holder.btn_tirios1.setOnClickListener {
            onChatClick?.onButtonOption1Click(position)
        }

        holder.btn_tirios2.setOnClickListener {
            onChatClick?.onButtonOption2Click(position)
        }
    }

    fun setOnChatClickListener(onChatClicks: OnChatClick) {
        this.onChatClick = onChatClicks
    }

    interface OnChatClick {
        fun onImageClick(position: Int)
        fun onVideoClick(position: Int)
        fun onButtonOption1Click(position: Int)
        fun onButtonOption2Click(position: Int)
    }
}








