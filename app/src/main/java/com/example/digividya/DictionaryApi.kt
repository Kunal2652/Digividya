package com.example.digividya

import DictionaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("v2/entries/en/{word}")
    fun getWordDefinition(@Path("word") word: String): Call<List<DictionaryResponse>>
}
