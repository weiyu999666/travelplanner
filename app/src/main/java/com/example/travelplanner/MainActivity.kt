package com.example.travelplanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val destinationEditText = findViewById<EditText>(R.id.destinationEditText)
        val dateEditText = findViewById<EditText>(R.id.dateEditText)
        val planButton = findViewById<Button>(R.id.planButton)


        planButton.setOnClickListener {
            val destination = destinationEditText.text.toString()
            val date = dateEditText.text.toString()
            val intent = Intent(this, PlanDetailsActivity::class.java).apply {
                putExtra("DESTINATION", destination)
                putExtra("DATE", date)
            }
            startActivity(intent)
        }
    }


}


