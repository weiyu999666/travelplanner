package com.example.travelplanner

import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.os.*
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.core.app.*

class PlanDetailsActivity : ComponentActivity() {

    private val CHANNEL_ID = "travel_plan_channel"
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan_details_activity)

        val destination = intent.getStringExtra("DESTINATION").toString()
        val date = intent.getStringExtra("DATE").toString()

        val destinationTextView = findViewById<TextView>(R.id.destinationTextView)
        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        val alertButton = findViewById<Button>(R.id.alertButton)


        destinationTextView.text = "Destination: $destination"
        dateTextView.text = "Travel Date: $date"



        alertButton.setOnClickListener {
            print("showNotiAlert")
            showNotiAlert(destination,date)
        }
    }

    private fun showNotiAlert(destination: String, date: String) {
        if (checkNotificationPermission()) {
            createNotificationChannel()
            sendNotification(destination ?: "", date ?: "")
        } else {
            requestNotificationPermission()
        }
    }


        private fun checkNotificationPermission(): Boolean {
        // For demonstration, assume API 35 introduces a new notification permission
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NotificationManagerCompat.from(this).areNotificationsEnabled()
        } else {
            true  // Notifications are enabled by default on older Android versions
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        }
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Travel Plan Notification"
            val descriptionText = "Channel for Travel Plan Notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(destination: String, date: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_travel)  // Ensure this icon exists
            .setContentTitle("Upcoming Trip")
            .setContentText("Destination: $destination on $date")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(1, notification)
        }
    }

}