package com.example.digividya.class10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R
import com.example.digividya.clases.cclass10
import com.example.digividya.clases.cclass11
import com.example.digividya.clases.cclass12
import com.example.digividya.clases.cclass9

class RecordedLecturesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Button variables
        val class9 : Button= findViewById(R.id.class9)
        val class10 : Button = findViewById(R.id.class10)
        val class11 : Button = findViewById(R.id.class11)
        val class12 : Button = findViewById(R.id.class12)

        // Navigation logic
        class9.setOnClickListener {
            startActivity(Intent(this, cclass9::class.java))
        }

        class10.setOnClickListener {
            startActivity(Intent(this, cclass10::class.java))
        }

        class11.setOnClickListener {
            startActivity(Intent(this, cclass11::class.java))
        }

        class12.setOnClickListener {
            startActivity(Intent(this, cclass12::class.java))
        }
    }
}
