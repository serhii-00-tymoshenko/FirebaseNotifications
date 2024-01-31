package com.serhiitymoshenko.firebasenotifications.cloudmessaging

import android.icu.text.CaseMap.Title
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.serhiitymoshenko.firebasenotifications.helpers.NotificationsHelper

class MyFirebaseMessagingIOService : FirebaseMessagingService() {

    private val notificationsHelper = NotificationsHelper()

    override fun onCreate() {
        super.onCreate()

        notificationsHelper.setNotificationManager(this)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            val notificationBody = message.notification?.body
            val notificationTitle = message.notification?.title

            notificationsHelper.sendNotification(this, notificationTitle, notificationBody)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}