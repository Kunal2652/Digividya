package com.example.digividya.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Video
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.quizes.QuizzesActivity
import com.example.digividya.R
import com.example.digividya.clases.RecordedLecturesActivity
import com.example.digividya.dictionary.dictionary
import com.example.digividya.notes.Notess
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import com.example.digividya.task.todo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar

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
        val quizzbtn: ImageButton = findViewById(R.id.quizzbtn)
        val dictionarybtn: ImageButton = findViewById(R.id.dictionarybtn)
        // Navigation
        btnnotes.setOnClickListener {
            navigateTo(Notess::class.java)
        }
        dictionary1.setOnClickListener {
            val intent = Intent(this, dictionary::class.java)
            startActivity(intent)
        }
        profilee.setOnClickListener {
            navigateTo(Profile::class.java)
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

        quizzbtn.setOnClickListener {
           navigateTo(QuizzesActivity::class.java)
        }
dictionarybtn.setOnClickListener {
    navigateTo(dictionary::class.java)
}

        val animatee: ImageView = findViewById(R.id.animate)
        val images = listOf(R.drawable.dash1, R.drawable.team, R.drawable.img1)
        var index = 0

        val imageSwitcher = object : Runnable {
            override fun run() {
                animatee.setImageResource(images[index])
                index = (index + 1) % images.size // Move to the next image
                animatee.postDelayed(this, 2000) // Repeat every 2 seconds
            }
        }

// Start switching images automatically
        animatee.postDelayed(imageSwitcher, 2000)

//calender present date
        val calendar = findViewById<CalendarView>(R.id.calenderr)

// Create a Calendar instance
        val cal = Calendar.getInstance()

// Set minDate to July 1, 2024
        cal.set(2024, Calendar.JULY, 1, 0, 0, 0)
        val minDate = cal.timeInMillis

// Set maxDate to July 1, 2026
        cal.set(2026, Calendar.JULY, 1, 23, 59, 59)
        val maxDate = cal.timeInMillis

// Apply the date range
        calendar.minDate = minDate
        calendar.maxDate = maxDate

// Update the calendar date every second
        lifecycleScope.launch {

                calendar.setDate(System.currentTimeMillis())

        }

        // Initialize VideoView
        val videoView = findViewById<VideoView>(R.id.videos)

// Provide the correct video URI (for raw resources)
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.video}")

// Set video URI
        videoView.setVideoURI(videoUri)

// Set up MediaController (optional)
        val mediaController = android.widget.MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

// Start video automatically
        videoView.start()

// Loop the video when it finishes
        videoView.setOnCompletionListener {
            videoView.start() // Restart video
        }



    }
    private fun navigateTo(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}
