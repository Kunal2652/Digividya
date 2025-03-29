package com.example.digividya

import DictionaryResponse
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class dictionary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dictionary)

        val searchBox = findViewById<EditText>(R.id.searchBox)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        searchButton.setOnClickListener {
            val word = searchBox.text.toString().trim()
            if (word.isNotEmpty()) {
                fetchDefinition(word, resultText)
            }
        }
    }
    private fun fetchDefinition(word: String, resultText: TextView) {
        RetrofitClient.instance.getWordDefinition(word).enqueue(object : Callback<List<DictionaryResponse>> {
            override fun onResponse(call: Call<List<DictionaryResponse>>, response: Response<List<DictionaryResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val wordData = response.body()!!.first()
                    val definition = wordData.meanings.firstOrNull()?.definitions?.firstOrNull()?.definition ?: "Definition not found"
                    resultText.text = "Word: ${wordData.word}\nDefinition: $definition"
                } else {
                    resultText.text = "Word not found!"
                    println("API Response Failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<DictionaryResponse>>, t: Throwable) {
                resultText.text = "Error: ${t.message}"
                t.printStackTrace() // This will show errors in Logcat
            }
        })
    }}
