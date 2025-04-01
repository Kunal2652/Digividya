package com.example.digividya.login

data class Users(
    var name: String = "",
    var username: String = "",
    var mobile: String = "",
    var email: String = "",
    var imageUrl: String = ""  // New field for profile picture
)