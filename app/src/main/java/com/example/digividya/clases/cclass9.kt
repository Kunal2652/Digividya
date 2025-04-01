package com.example.digividya.clases

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R

class cclass9 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.class9)

        // Button variables
        val eng9 = findViewById<Button>(R.id.eng9)
        val math9 = findViewById<Button>(R.id.math9)
        val sst9 = findViewById<Button>(R.id.sst9)
        val sci9 = findViewById<Button>(R.id.sci9)

        // Navigation logic
        eng9.setOnClickListener {
            startActivity(Intent(this, eng9::class.java)) // Replace Eng9Activity with the actual activity class
        }

        math9.setOnClickListener {
            startActivity(Intent(this, math9::class.java)) // Replace Math9Activity with the actual activity class
        }

        sst9.setOnClickListener {
            startActivity(Intent(this, sst9::class.java)) // Replace Sst9Activity with the actual activity class
        }

        sci9.setOnClickListener {
            startActivity(Intent(this, sci9::class.java)) // Replace Sci9Activity with the actual activity class
        }
    }
}
