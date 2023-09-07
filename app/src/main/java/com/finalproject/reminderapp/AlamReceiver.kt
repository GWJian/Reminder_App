package com.finalproject.reminderapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class AlamReceiver : BroadcastReceiver() {

    //this is boardcastreceiver,we can do all the visual things here,like notification to the user or play a sound etc.
//    override fun onReceive(context: Context, intent: Intent) {
//        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
//        println("AlarmClockTrigger $message")
//    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return

        // Create a notification channel (required for Android Oreo and later)
        createNotificationChannel(context)

        // Define the intent to open your app's main activity
        val contentIntent = Intent(context, MainActivity::class.java)
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // You can pass any additional data to your main activity using extras if needed
        contentIntent.putExtra("EXTRA_KEY", "some_value")

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_important)
            .setContentTitle("Notification Title")
            .setContentText("AlarmClockTrigger $message")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(null)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            //ignore this error line
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    // Create a notification channel
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "Notification Channel Description"
            val importance =
                NotificationManager.IMPORTANCE_HIGH // Use high importance for heads-up notification
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Define the notification ID
    companion object {
        private const val CHANNEL_ID = "my_channel_id"
        private const val NOTIFICATION_ID = 1
    }


}