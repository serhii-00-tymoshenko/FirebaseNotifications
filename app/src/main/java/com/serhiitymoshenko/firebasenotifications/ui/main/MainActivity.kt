package com.serhiitymoshenko.firebasenotifications.ui.main

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.serhiitymoshenko.firebasenotifications.R
import com.serhiitymoshenko.firebasenotifications.databinding.ActivityMainBinding
import com.serhiitymoshenko.firebasenotifications.helpers.NotificationsHelper
import com.serhiitymoshenko.firebasenotifications.utils.TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted -> }

    private val notificationsHelper = NotificationsHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPostNotificationsPermission()
        notificationsHelper.setNotificationManager(this)
        setListeners(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            Log.d(TAG, token)
        }
    }

    private fun setListeners(context: Context) {
        binding.sendPush.setOnClickListener {
            notificationsHelper.sendNotification(context, "Title", "Text")
        }
    }

    private fun requestPostNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(POST_NOTIFICATIONS)
        }
    }
}