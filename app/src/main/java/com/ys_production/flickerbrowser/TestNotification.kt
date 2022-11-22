package com.ys_production.flickerbrowser

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap

internal class TestNotification : BaseClass() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_notification)
        setToolBar(true)
        scheduleNotification(this,5000,5)

//        val nm: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        createNotificationChannel(nm)
//        val notification: Any
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notification = Notification.Builder(this,getString(R.string.Channel_id)).setContentTitle("title")
//                .setContentText("cont text").setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setLargeIcon(getDrawable(R.drawable.ic_launcher_background)!!.toBitmap(100,100,null))
//            nm.notify(12,notification.build())
//
//        } else {
//            Toast.makeText(this, "${Build.VERSION.SDK_INT}", Toast.LENGTH_SHORT).show()
//        }
    }
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.Channel_id)
            val descriptionText = getString(R.string.Channel_dec)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.Channel_id), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
//            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(
        context: Context,
        delay: Long,
        notificationId: Int
    ) { //delay is after how much time(in millis) from current time you want to schedule the notification

        val builder = NotificationCompat.Builder(context,"Main")
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.app_name))
            .setAutoCancel(false)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//            .setLargeIcon(AppCompatResources.getDrawable()!!.toBitmap(100,100,Bitmap.Config.ALPHA_8))
        val intent = Intent(context, MainActivity::class.java)
        val activity = PendingIntent.getActivity(context,
            notificationId,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setContentIntent(activity)
        val notification = builder.build()
        val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(context,
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT)
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }
}