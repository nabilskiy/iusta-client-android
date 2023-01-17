package com.ls.iusta.core.push

import android.media.RingtoneManager
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import android.graphics.BitmapFactory
import androidx.navigation.NavDeepLinkBuilder
import com.ls.iusta.R
import com.ls.iusta.ui.MainActivity

class MessagingService : FirebaseMessagingService() {

        override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Timber.tag("logtag").d("Message ${p0.data}")
        val title = p0.data["title"]
        val message = p0.data["message"]
        val orderId = p0.data["orderId"]?.toLongOrNull()
        val notificationId = p0.data["notificationId"]?.toIntOrNull() ?: 0
        if (title != null && message != null && orderId != null && notificationId != 0) {
            showMessageNotification(title, message, orderId, notificationId)
        }
    }

    private fun showMessageNotification(
        title: String,
        message: String,
        orderId: Long,
        notificationId: Int
    ) {

        val args = Bundle()
        args.putLong("orderId", orderId)
        args.putInt("notificationId", notificationId)
        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.home_nav_graph)
            .setDestination(R.id.ticketDetailFragment)
            .setArguments(args)
            .createPendingIntent()

        val largeIcon = BitmapFactory.decodeResource(
            resources,
            R.drawable.message_large_icon
        )

        val notification = NotificationCompat.Builder(this, NotificationChannels.URGENT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(100, 200, 500, 500))
            .setSmallIcon(R.drawable.ic_message)
            .setLargeIcon(largeIcon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(this)
            .notify(orderId.toInt(), notification)
    }
}