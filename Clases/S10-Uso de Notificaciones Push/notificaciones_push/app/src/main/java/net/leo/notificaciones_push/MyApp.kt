package net.leo.notificaciones_push

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.tasks.Task

class MyApp : Application() {

    companion object{
        const val NOTIFICATION_CHANNEL_ID = "notification_fcm"
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task: Task<String> ->
            if (!task.isSuccessful) {
                println("El token no fue generado")
                return@addOnCompleteListener
            }

            val token = task.result
            println("El token es: $token")

            FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener { subscribeTask ->
                    if (subscribeTask.isSuccessful) {
                        println("Suscrito exitosamente al tema 'all'")
                    } else {
                        println("Error al suscribirse al tema 'all'")
                    }
                }
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notificaciones de FCM",
                NotificationManager.IMPORTANCE_HIGH,
            )
            channel.description = "Estas notificaciones van a ser recibidas desde FCM"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}