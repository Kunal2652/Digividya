package com.digividya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.LiveClassesActivity
import com.example.digividya.profile
import com.example.digividya.QuizzesActivity
import com.example.digividya.R
import com.example.digividya.RecordedLecturesActivity
import com.example.digividya.dictionary

import com.example.digividya.todo

import com.example.yourapp.CalculatorDialog

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        val profilee = findViewById<ImageButton>(R.id.profilee)
        val btnnotes: Button = findViewById(R.id.btnnotes)
        val btnRecordedLectures: Button = findViewById(R.id.btnRecordedLectures)
        val btnQuizzes: Button = findViewById(R.id.btnQuizzes)
//        val btnTools: Button = findViewById(R.id.btnTools)
        val todoo: Button = findViewById(R.id.btntodo)
        val dictionary1: Button = findViewById(R.id.dictionary1)
        // Navigation
        btnnotes.setOnClickListener {
            navigateTo(LiveClassesActivity::class.java)
        }
        dictionary1.setOnClickListener {
            val intent = Intent(this, dictionary::class.java)
            startActivity(intent)
        }
        profilee.setOnClickListener {
            navigateTo(profile::class.java)
        }

        todoo.setOnClickListener {
            navigateTo(todo::class.java)
        }
        btnRecordedLectures.setOnClickListener {
            navigateTo(RecordedLecturesActivity::class.java)
        }

        btnQuizzes.setOnClickListener {
            navigateTo(QuizzesActivity::class.java)
        }

//        btnTools.setOnClickListener {
//            val calculatorDialog = CalculatorDialog()
//            calculatorDialog.show(supportFragmentManager, "CalculatorDialog")
//        }

    }



    private fun navigateTo(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}
