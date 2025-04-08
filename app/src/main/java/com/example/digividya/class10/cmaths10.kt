package com.example.digividya.class10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R

class cmaths10 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.math10)

        // Define button IDs and corresponding YouTube video IDs
        val buttonIds = intArrayOf(
            R.id.c10m1, R.id.c10m2, R.id.c10m3, R.id.c10m4, R.id.c10m5, R.id.c10m6,R.id.c10m7, R.id.c10m8, R.id.c10m9, R.id.c10m10
        )

        val videoIds = arrayOf(
            "pRNEWJdLhJA",
            "iw5FIkdVp2c",
            "bl-TDU00UGM",
            "Htk2kC-SHII",
            "oydSOw7QJsc",
            "YxVhwG6KA8o",
            "2TZXGcV11ls",
            "q0SPrihosoM",
            "jeXiWiqsKvM",
            "stfn4Uucjxs"
        )

        // Assign click listeners to each button
        for (i in buttonIds.indices) {
            val index = i // final variable for lambda expression
            val button = findViewById<Button>(buttonIds[i])
            button.setOnClickListener { v: View? -> openYouTubePlayerActivity(videoIds[index]) }
        }
    }

    // Method to start YouTubePlayerActivity and pass the video ID
    private fun openYouTubePlayerActivity(videoId: String) {
        val intent = Intent(this, YouTubePlayerActivity::class.java)
        intent.putExtra("VIDEO_ID", videoId)  // Pass the YouTube video ID
        startActivity(intent)
    }
}
