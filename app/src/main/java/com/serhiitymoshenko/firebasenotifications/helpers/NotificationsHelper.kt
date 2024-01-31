package com.serhiitymoshenko.firebasenotifications.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.serhiitymoshenko.firebasenotifications.R
import com.serhiitymoshenko.firebasenotifications.ui.main.MainActivity
import com.serhiitymoshenko.firebasenotifications.utils.NOTIFICATION_CHANNEL_ID
import com.serhiitymoshenko.firebasenotifications.utils.NOTIFICATION_CHANNEL_NAME
import com.serhiitymoshenko.firebasenotifications.utils.NOTIFICATION_REQUEST_CODE

class NotificationsHelper {

    private lateinit var notificationManager: NotificationManager

    fun sendNotification(context: Context, title: String?, body: String?) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingNotificationIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingNotificationIntent)

        createNotificationChannel()

        notificationManager.notify(NOTIFICATION_REQUEST_CODE, notificationBuilder.build())
    }

    fun setNotificationManager(context: Context) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}