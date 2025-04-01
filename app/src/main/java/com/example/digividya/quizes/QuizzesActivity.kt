package com.example.digividya.quizes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R

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
