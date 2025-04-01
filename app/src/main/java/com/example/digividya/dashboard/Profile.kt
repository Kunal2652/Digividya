package com.example.digividya.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.digividya.R
import com.example.digividya.login.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage

    private lateinit var profileImageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var mobileEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var editButton: Button
    private lateinit var changePhotoButton: Button

    private var imageUri: Uri? = null
    private var isEditing = false

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("datauser")
        storage = FirebaseStorage.getInstance()

        profileImageView = findViewById(R.id.profileImageView)
        nameEditText = findViewById(R.id.nameTextView)
        usernameEditText = findViewById(R.id.usernameTextView)
        mobileEditText = findViewById(R.id.mobileTextView)
        emailEditText = findViewById(R.id.emailTextView)
        editButton = findViewById(R.id.editButton)
        changePhotoButton = findViewById(R.id.changePhotoButton)

        setEditable(false)

        val userId = auth.currentUser?.uid
        if (userId != null) {
            fetchUserData(userId)
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show()
        }

        editButton.setOnClickListener {
            if (isEditing) {
                saveUserData()
            } else {
                setEditable(true)
            }
        }

        changePhotoButton.setOnClickListener {
            openGallery()
        }
    }

    private fun fetchUserData(userId: String) {
        database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(Users::class.java)
                    if (user != null) {
                        nameEditText.setText(user.name)
                        usernameEditText.setText(user.username)
                        mobileEditText.setText(user.mobile)
                        emailEditText.setText(user.email)

                        // Load profile image
                        if (user.imageUrl.isNotEmpty()) {
                            Glide.with(this@profile)
                                .load(user.imageUrl)
                                .into(profileImageView)
                        }
                    } else {
                        Toast.makeText(this@profile, "Failed to parse user data.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@profile, "User data not found.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfileActivity", "Database error: ${error.message}")
                Toast.makeText(this@profile, "Failed to load user data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            profileImageView.setImageURI(imageUri)
            uploadImage()
        }
    }

    private fun uploadImage() {
        val userId = auth.currentUser?.uid ?: return
        val imageRef = storage.reference.child("profile_images/$userId.jpg")

        imageUri?.let {
            imageRef.putFile(it)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        saveImageUrl(uri.toString())
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Image upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveImageUrl(imageUrl: String) {
        val userId = auth.currentUser?.uid ?: return
        database.child(userId).child("imageUrl").setValue(imageUrl)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile photo updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save photo: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setEditable(editable: Boolean) {
        nameEditText.isEnabled = editable
        usernameEditText.isEnabled = editable
        mobileEditText.isEnabled = editable
        emailEditText.isEnabled = editable

        editButton.text = if (editable) "Save" else "Edit"
        isEditing = editable
    }

    private fun saveUserData() {
        val userId = auth.currentUser?.uid ?: return

        val updatedUser = Users(
            name = nameEditText.text.toString(),
            username = usernameEditText.text.toString(),
            mobile = mobileEditText.text.toString(),
            email = emailEditText.text.toString()
        )

        database.child(userId).setValue(updatedUser)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                setEditable(false)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update profile.", Toast.LENGTH_SHORT).show()
            }
    }
}
