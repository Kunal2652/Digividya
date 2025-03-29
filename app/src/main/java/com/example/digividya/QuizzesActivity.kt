package com.example.digividya
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

class QuizzesActivity : AppCompatActivity() {

    private lateinit var startQuizButton: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quizact)

        startQuizButton = findViewById(R.id.startQuizButton)




        startQuizButton.setOnClickListener {
            // Start the quiz activity when the button is clicked
            val intent = Intent(this, Qprolos::class.java)
            startActivity(intent)
        }
    }
}
