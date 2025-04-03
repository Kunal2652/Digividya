package com.example.digividya.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R
import com.example.digividya.clases.cclass10
import com.example.digividya.clases.cclass11
import com.example.digividya.clases.cclass12
import com.example.digividya.clases.cclass9

class Notess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cnotes)
        // Button variables
        val class9 : Button = findViewById(R.id.notes9)
        val class10 : Button = findViewById(R.id.notes10)
        val class11 : Button = findViewById(R.id.notes11)
        val class12 : Button = findViewById(R.id.notes12)

        // Navigation logic
        class9.setOnClickListener {
            startActivity(Intent(this, nclas9::class.java))
        }

        class10.setOnClickListener {
            startActivity(Intent(this, nclas10::class.java))
        }

        class11.setOnClickListener {
            startActivity(Intent(this, nclas11::class.java))
        }

        class12.setOnClickListener {
            startActivity(Intent(this, nclas12::class.java))
        }
    }
}