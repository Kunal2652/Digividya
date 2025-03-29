package com.example.digividya

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.digividya.MainActivity
import com.example.digividya.R
import com.example.yourapp.CalculatorDialog

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class Qprolos : AppCompatActivity() {
    private lateinit var questionText: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var checkAnsTextView: TextView
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    private var questionList = mutableListOf<Question>() // Store 12 questions
    private var currentQuestionIndex = 0
    private var correctAnswer = ""
    private var selectedAnswer: String? = null  // Store selected answer
    private var isAnswered = false  // Prevent multiple submissions
    private var score = 0  // Track the score

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qprolos)

        questionText = findViewById(R.id.questionText)
        optionButtons = listOf(
            findViewById(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )
        checkAnsTextView = findViewById(R.id.checkans)
        submitButton = findViewById(R.id.submit)
        nextButton = findViewById(R.id.nextButton)
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        val btncal = findViewById<Button>(R.id.btnc)
        btncal.setOnClickListener {
            val calculatorDialog = CalculatorDialog()
            calculatorDialog.show(supportFragmentManager, "CalculatorDialog")
        }
        // Back Button
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this@Qprolos, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Load 12 questions
        fetchQuizQuestions()

        // Handle answer selection
        for (button in optionButtons) {
            button.setOnClickListener { onOptionSelected(button) }
        }

        // Submit answer button
        submitButton.setOnClickListener { checkAnswer() }

        // Next question button
        nextButton.setOnClickListener { loadNextQuestion() }
    }

    // Fetch 12 questions from API
    private fun fetchQuizQuestions() {
        questionList.clear()
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                for (i in 1..12) {  // Get 12 questions
                    val url = URL("https://aptitude-api.vercel.app/Random")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    if (connection.responseCode == 200) {
                        val response = connection.inputStream.bufferedReader().use { it.readText() }
                        Log.d("API_RESPONSE", "Raw JSON: $response")

                        val jsonObject = JSONObject(response)
                        val question = jsonObject.getString("question")
                        val options = jsonObject.getJSONArray("options")
                        val answer = jsonObject.getString("answer")

                        questionList.add(Question(question, options, answer))
                    }
                    connection.disconnect()
                }

                runOnUiThread {
                    if (questionList.isNotEmpty()) {
                        loadNextQuestion()
                    } else {
                        Toast.makeText(
                            this@Qprolos,
                            "Failed to load questions!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching questions: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@Qprolos, "API Error!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Load the next question
    private fun loadNextQuestion() {
        if (currentQuestionIndex < questionList.size) {
            // Load the next question normally
            val currentQuestion = questionList[currentQuestionIndex]
            questionText.text = currentQuestion.question
            correctAnswer = currentQuestion.answer
            selectedAnswer = null
            isAnswered = false

            for (i in optionButtons.indices) {
                optionButtons[i].text = currentQuestion.options.getString(i)
                optionButtons[i].isEnabled = true
                optionButtons[i].setBackgroundColor(resources.getColor(R.color.default_option))
            }

            checkAnsTextView.text = ""
            submitButton.isEnabled = false
            nextButton.isEnabled = false

            progressBar.progress = ((currentQuestionIndex) * 100) / 12
            progressText.text = " ${currentQuestionIndex + 1} of 12"

            currentQuestionIndex++

        } else {
            // ✅ Make sure score is passed correctly to ResultActivity
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("score", score)  // Send actual score
            intent.putExtra("totalQuestions", 12)
            startActivity(intent)
            finish()
        }
    }


    // Handle option selection (allows changing answer before submission)
    private fun onOptionSelected(selectedButton: Button) {
        if (!isAnswered) { // Allow selection only before submitting
            selectedAnswer = selectedButton.text.toString()

            // Highlight selected button
            for (button in optionButtons) {
                button.isSelected = (button == selectedButton)
            }

            submitButton.isEnabled = true // Enable submit button after selection
        }
    }

    // Check the answer
    private fun checkAnswer() {
        if (selectedAnswer == null) {
            Toast.makeText(this, "Please select an answer!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isAnswered) { // Ensure submission happens only once
            for (button in optionButtons) {
                if (button.text.toString() == correctAnswer) {
                    button.setBackgroundColor(resources.getColor(R.color.green)) // Green for correct answer
                }
                if (button.text.toString() == selectedAnswer) {
                    if (selectedAnswer == correctAnswer) {
                        checkAnsTextView.text = "✅ Correct!"
                        score++  // Increment score for correct answer
                        button.setBackgroundColor(resources.getColor(R.color.green)) // Green for correct
                    } else {
                        checkAnsTextView.text = "❌ Incorrect! The correct answer is: $correctAnswer"
                        button.setBackgroundColor(resources.getColor(R.color.red)) // Red for incorrect
                    }
                }
                button.isEnabled = false // Disable all buttons after submission
            }

            // Disable submit button after submission
            submitButton.isEnabled = false
            nextButton.isEnabled = true  // Enable next button to move forward
            isAnswered = true // Mark question as answered
        }
    }
}

// Data class for a question
data class Question(
    val question: String,
    val options: JSONArray,
    val answer: String
)
