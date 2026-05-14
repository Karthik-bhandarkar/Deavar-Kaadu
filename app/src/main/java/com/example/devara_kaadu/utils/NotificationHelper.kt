package com.example.devara_kaadu.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.devara_kaadu.MainActivity
import com.example.devara_kaadu.R
import java.util.concurrent.TimeUnit

object NotificationHelper {
    private const val CHANNEL_ID = "devara_kaadu_reminders"
    private const val CHANNEL_NAME = "Sacred Grove Reminders"
    private const val WORK_TAG = "daily_reminder"

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Daily reminders to protect Karnataka's Sacred Groves"
        }
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(channel)
    }

    fun scheduleDailyReminder(context: Context) {
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setConstraints(Constraints.Builder().build())
            .addTag(WORK_TAG)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, request
        )
    }

    fun showReminder(context: Context, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(System.currentTimeMillis().toInt(), notification)
    }
}

/** WorkManager worker for periodic conservation reminders. */
class ReminderWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    private val messages = listOf(
        Pair("🌿 Protect Sacred Groves", "Visit a nearby Devara Kaadu and report any threats today."),
        Pair("🦋 Biodiversity Guardian", "Karnataka has 2000+ sacred groves. Help document their species."),
        Pair("💧 Water Steward", "Sacred groves recharge our rivers. Protect them for future generations."),
        Pair("🌳 Sacred Sentinel", "Report illegal tree cutting or waste dumping in sacred groves.")
    )

    override fun doWork(): Result {
        val (title, msg) = messages.random()
        NotificationHelper.showReminder(applicationContext, title, msg)
        return Result.success()
    }
}
