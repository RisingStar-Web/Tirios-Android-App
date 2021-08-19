package com.ai.tirios.ui.notification

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.AudioAttributes
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.ui.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class TFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("T_NOTIFICATION", p0)
        SharedStorage(applicationContext).setDeviceId(p0)
    }

    override fun onMessageReceived(rm: RemoteMessage) {
        super.onMessageReceived(rm)
        try {
            val remoteMessageDate = rm.data
            if (!isAppForground(applicationContext)) {
                setPendingIntent()
            } else {
                val remoteMessage = rm.notification
                remoteMessage?.let { rMsg ->
                    val intent = Intent(applicationContext, SplashActivity::class.java)
                    intent.putExtra(EXTRA_NOTIFICATION, "")
                    val pi = PendingIntent.getActivity(applicationContext, 101, intent, 0)

                    val nm = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    var channel: NotificationChannel? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val att = AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build()
                        channel = NotificationChannel(
                            "222",
                            "my_channel",
                            NotificationManager.IMPORTANCE_HIGH
                        )
                        nm.createNotificationChannel(channel)
                    }

                    val builder = NotificationCompat.Builder(
                        applicationContext, "222"
                    )
                        .setContentTitle(rMsg.title)
                        .setAutoCancel(true)
                        .setLargeIcon((getDrawable(R.mipmap.tirios_app_icon) as BitmapDrawable?)!!.bitmap)
                        .setSmallIcon(R.mipmap.tirios_app_icon) //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.electro))
                        .setContentText(rMsg.body)
                        .setContentIntent(pi)

                    builder.priority = NotificationCompat.PRIORITY_HIGH
                    nm.notify(101, builder.build())
                }
            }
        } catch (exc: Exception) {
            Log.d("Exception", exc.toString());
        }
    }

    private fun isAppForground(mContext: Context): Boolean {
        val am = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.getRunningTasks(1)
        if (tasks.isNotEmpty()) {
            val topActivity = tasks[0].topActivity
            if (topActivity!!.packageName != mContext.packageName) {
                return false
            }
        }
        return true
    }

    private fun setPendingIntent() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_tirios_app_icon)
            .setContentTitle("Tirios")
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val intent = Intent(applicationContext, SplashActivity::class.java)
        intent.putExtra(EXTRA_NOTIFICATION, "")
        builder.setContentIntent(
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        ).setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(89889, builder.build())
        }
    }

    companion object {
        const val CHANNEL_ID = "trios_default_channel"
        const val EXTRA_NOTIFICATION = "extra_notification"
    }
}