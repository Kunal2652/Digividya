package com.example.digividya.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register) // Your provided XML layout

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("datauser") // Set the root node to 'datauser'

        // Find views by ID
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginstatus = findViewById<Button>(R.id.loginstatus)
        val textPersonName = findViewById<EditText>(R.id.name)
        val phone = findViewById<EditText>(R.id.mobile)
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)

        // Register button click listener
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val name = textPersonName.text.toString().trim()
            val mobile = phone.text.toString().trim()
            val username = usernameEditText.text.toString().trim()

            // Input validation
            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || mobile.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password, name, mobile, username)
            }
        }

        // Login status button click listener
        loginstatus.setOnClickListener {
            navigateTo(login::class.java)
        }
    }

    // Register user with Firebase and save data to 'datauser' node
    private fun registerUser(email: String, password: String, name: String, mobile: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    Log.d("RegisterActivity", "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        val userData = User(name, username, mobile, email)

                        // Save user data to 'datauser' node
                        database.child(userId).setValue(userData)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                                    Log.d("RegisterActivity", "User data saved successfully under 'datauser'!")
                                    navigateTo(login::class.java) // Navigate to the login activity
                                } else {
                                    val error = dbTask.exception?.localizedMessage ?: "Unknown error occurred."
                                    Toast.makeText(this, "Failed to save user data: $error", Toast.LENGTH_SHORT).show()
                                    Log.e("RegisterActivity", "saveUserData:failure", dbTask.exception)
                                }
                            }
                    }
                } else {
                    // Registration failed
                    Log.e("RegisterActivity", "createUserWithEmail:failure", task.exception)
                    val errorMessage = task.exception?.localizedMessage ?: "Unknown error occurred."
                    Toast.makeText(this, "Registration failed: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Function to navigate to another activity
    private fun navigateTo(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }
}

// User data class for storing user details in Firebase Database
data class User(
    val name: String = "",
    val username: String= "",
    val mobile: String= "",
    val email: String= ""
)
