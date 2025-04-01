package com.example.digividya.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.dashboard.MainActivity
import com.example.digividya.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Initialize UI components
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.button2)
        val loginStatus = findViewById<TextView>(R.id.textView)
        val registrationButton = findViewById<Button>(R.id.registeration)

        // Navigate to the registration screen
        registrationButton.setOnClickListener {
            startActivity(Intent(this, register::class.java))
        }

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Authentication for login
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        Log.d("LoginActivity", "signInWithEmail:success")
                        val user: FirebaseUser? = auth.currentUser
                        loginStatus.text = "Login successful!"
                        Toast.makeText(this, "Welcome, ${user?.email}", Toast.LENGTH_SHORT).show()

                        // Navigate to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login failed
                        Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                        loginStatus.text = "Login failed! Invalid credentials."
                        Toast.makeText(
                            this,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }}
