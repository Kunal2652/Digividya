package com.example.digividya.quizes

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qresult)

        // ✅ Get the correct score from Intent
        val score = intent.getIntExtra("score", -1)  // Default to -1 to check if data is missing
        val totalQuestions = intent.getIntExtra("totalQuestions", 12)

        // ✅ Debugging Log (Check if data is received)
        println("ResultActivity: Received Score = $score, Total Questions = $totalQuestions")

        // Bind views
        val tvScore: TextView = findViewById(R.id.tvtScore)
        val tvCorrect: TextView = findViewById(R.id.tvCorrect)
        val tvIncorrect: TextView = findViewById(R.id.tvIncorrect)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        // ✅ Prevent displaying incorrect values
        if (score == -1) {
            tvScore.text = "Error: Score not received"
            return
        }

        // Display the results
        tvScore.text = "Score: $score/$totalQuestions"
        tvCorrect.text = "Correct Answers: $score"
        tvIncorrect.text = "Incorrect Answers: ${totalQuestions - score}"

        // ✅ Update progress bar
        progressBar.progress = (score * 100) / totalQuestions
    }
}
