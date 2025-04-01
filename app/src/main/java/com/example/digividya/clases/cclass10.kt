package com.example.digividya.clases

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R
import com.example.digividya.class10.ceng10
import com.example.digividya.class10.cmaths10
import com.example.digividya.class10.csci10
import com.example.digividya.class10.csst10

class cclass10 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.class10)
        val sci10 : Button = findViewById(R.id.sci10)
        val eng10 : Button = findViewById(R.id.eng10)
        val math10 : Button = findViewById(R.id.math10)
        val sst10 : Button = findViewById(R.id.sst10)


        sci10.setOnClickListener {
            startActivity(Intent(this, csci10::class.java))
        }

        eng10.setOnClickListener {
            startActivity(Intent(this, csst10::class.java))
        }

        math10.setOnClickListener {
            startActivity(Intent(this, cmaths10::class.java))
        }

        sst10.setOnClickListener {
            startActivity(Intent(this, ceng10::class.java))
        }
    }
}