package com.example.digividya.class9

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R
import com.example.digividya.class10.YouTubePlayerActivity

class cmath9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.math9)

//        // Define button IDs and corresponding YouTube video IDs
//        val buttonIds = intArrayOf(
//            R.id.c9m1, R.id.c9m2, R.id.c9m3, R.id.c9m4, R.id.c9m5, R.id.c9m6,R.id.c9m7, R.id.c9m8, R.id.c9m9, R.id.c9m10
//        )
//
//        val videoIds = arrayOf(
//            "T2QnlGlJuu8",  // Video ID for the first video
//            "qP30DUgocw4",  // Video ID for the second video
//            "iVm9ilsqpOo",  // Video ID for the third video
//            "dx4Teh-nv3A",  // Video ID for the fourth video
//            "IhJ_h0NmZlI",  // Video ID for the fifth video
//            "SyYMFulCGOU",  // Video ID for the sixth video
//            "SLEGr2FKakE",  // Video ID for the seventh video
//            "CwP5ZbTge0g",  // Video ID for the eighth video
//            "ZMjacJB7Dig",  // Video ID for the ninth video
//            "UJRETS_SjpA"   // Video ID for the tenth video
//        )
//
//        // Assign click listeners to each button
//        for (i in buttonIds.indices) {
//            val index = i // final variable for lambda expression
//            val button = findViewById<Button>(buttonIds[i])
//            button.setOnClickListener { v: View? -> openYouTubePlayerActivity(videoIds[index]) }
//        }
//    }
//
//    // Method to start YouTubePlayerActivity and pass the video ID
//    private fun openYouTubePlayerActivity(videoId: String) {
//        val intent = Intent(this, YouTubePlayerActivity::class.java)
//        intent.putExtra("VIDEO_ID", videoId)  // Pass the YouTube video ID
//        startActivity(intent)
    }
}
