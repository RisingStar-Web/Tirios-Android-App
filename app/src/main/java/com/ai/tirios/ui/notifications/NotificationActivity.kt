package com.ai.tirios.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityNotificationBinding
import com.ai.tirios.dataclasses.BodyNotificationRead
import com.ai.tirios.listeners.AdapterItemClick
import javax.inject.Inject

class NotificationActivity : BaseActivity<ActivityNotificationBinding,
        NotificationViewModel>(),
    NotificationMedium, AdapterItemClick {

    private var binding: ActivityNotificationBinding? = null
    var sharedStorage: SharedStorage? = null
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding?.notifications = viewModel
        sharedStorage = SharedStorage(this)

        adapter = NotificationAdapter(this, viewModel.notification_array, this)

        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding!!.rcvNotifications.layoutManager = linearLayoutManager
        binding!!.rcvNotifications.adapter = adapter

        sharedStorage!!.getid()?.let { viewModel.getNotificatonList(it) }

        viewModel.notification_list.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all

    override val layoutId: Int
        get() = R.layout.activity_notification

    override lateinit var viewModel: NotificationViewModel
        @Inject internal set

    override fun onBackArrowPressed() {
        finish()
    }

    override fun onItemClick(position: Int, id: Int, type: String) {
        val NotificationID = BodyNotificationRead(id.toString())
        viewModel.onNotificationClick(NotificationID)
    }

}