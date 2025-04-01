package com.example.digividya.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.quizes.QuizzesActivity
import com.example.digividya.R
import com.example.digividya.class10.RecordedLecturesActivity
import com.example.digividya.dictionary.dictionary
import com.example.digividya.notes.notess

import com.example.digividya.task.todo

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
            navigateTo(notess::class.java)
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
