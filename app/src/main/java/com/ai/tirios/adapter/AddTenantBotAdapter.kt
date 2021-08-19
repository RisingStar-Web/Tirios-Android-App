package com.ai.tirios.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ai.tirios.R
import com.ai.tirios.custom.SFProRegularTextView
import com.ai.tirios.dataclasses.Message
import com.bumptech.glide.Glide

class AddTenantBotAdapter(mContext: Context, chatList: ArrayList<Message>) :
    RecyclerView.Adapter<AddTenantBotAdapter.MyViewHolder>() {
    private var con = mContext
    private var chat = chatList
    private var onChatClick: OnChatClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_add_tenant_chat_bot, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chat.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ll_tirios: LinearLayout
        var ll_self: LinearLayout
        var ll_btnTirios: LinearLayout
        var ll_btnTiriosMem1: LinearLayout
        var tv_msgtirios: SFProRegularTextView
        var tv_msgSelf: SFProRegularTextView
        var iv_msgPic: ImageView
        var btn_tirios1: Button
        var btn_tirios2: Button
        var btn_tiriosMem1: Button
        var btn_tiriosMem2: Button
        var btn_tiriosMem3: Button
        var btn_tiriosMem4: Button
        var btn_tiriosMem5: Button

        init {
            ll_tirios = view.findViewById(R.id.ll_tirios) as LinearLayout
            ll_self = view.findViewById(R.id.ll_self) as LinearLayout
            ll_btnTirios = view.findViewById(R.id.ll_btnTirios) as LinearLayout
            ll_btnTiriosMem1 = view.findViewById(R.id.ll_btnTiriosMem1) as LinearLayout
            tv_msgtirios = view.findViewById(R.id.tv_msgtirios) as SFProRegularTextView
            tv_msgSelf = view.findViewById(R.id.tv_msgSelf) as SFProRegularTextView
            iv_msgPic = view.findViewById(R.id.iv_msgPic) as ImageView
            btn_tirios1 = view.findViewById(R.id.btn_tirios1) as Button
            btn_tirios2 = view.findViewById(R.id.btn_tirios2) as Button
            btn_tiriosMem1 = view.findViewById(R.id.btn_tiriosMem1) as Button
            btn_tiriosMem2 = view.findViewById(R.id.btn_tiriosMem2) as Button
            btn_tiriosMem3 = view.findViewById(R.id.btn_tiriosMem3) as Button
            btn_tiriosMem4 = view.findViewById(R.id.btn_tiriosMem4) as Button
            btn_tiriosMem5 = view.findViewById(R.id.btn_tiriosMem5) as Button
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
//                println("bitmap=====>"+chat.get(position).url)
                Glide.with(con).load(chat.get(position).imageBitMap).into(holder.iv_msgPic)
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
            if (chat.get(position).type == Message.Type.BUTTON) {
                holder.ll_btnTiriosMem1.visibility = View.VISIBLE
                holder.tv_msgtirios.visibility = View.VISIBLE
                holder.tv_msgtirios.text = chat.get(position).message
                holder.btn_tiriosMem1.text = chat.get(position).btnOption1
                holder.btn_tiriosMem2.text = chat.get(position).btnOption2
                holder.btn_tiriosMem3.text = chat.get(position).btnOption3
                holder.btn_tiriosMem4.text = chat.get(position).btnOption4
                holder.btn_tiriosMem5.text = chat.get(position).btnOption5
            }

        }

        holder.iv_msgPic.setOnClickListener {
                onChatClick?.onImageClick(position)
        }

        holder.btn_tirios1.setOnClickListener {
            onChatClick?.onButtonOption1Click(position)
        }

        holder.btn_tirios2.setOnClickListener {
            onChatClick?.onButtonOption2Click(position)
        }

        holder.btn_tiriosMem1.setOnClickListener {
            onChatClick?.onButtonOption1Click(position)
        }

        holder.btn_tiriosMem2.setOnClickListener {
            onChatClick?.onButtonOption2Click(position)
        }

        holder.btn_tiriosMem3.setOnClickListener {
            onChatClick?.onButtonOption3Click(position)
        }

        holder.btn_tiriosMem4.setOnClickListener {
            onChatClick?.onButtonOption4Click(position)
        }

        holder.btn_tiriosMem5.setOnClickListener {
            onChatClick?.onButtonOption5Click(position)
        }
    }

    fun setOnChatClickListener(onChatClicks: OnChatClick) {
        this.onChatClick = onChatClicks
    }

    interface OnChatClick {
        fun onImageClick(position: Int)
        fun onButtonOption1Click(position: Int)
        fun onButtonOption2Click(position: Int)
        fun onButtonOption3Click(position: Int)
        fun onButtonOption4Click(position: Int)
        fun onButtonOption5Click(position: Int)
    }
}








