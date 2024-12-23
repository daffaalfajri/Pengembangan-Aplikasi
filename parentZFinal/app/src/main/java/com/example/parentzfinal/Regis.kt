package com.example.parentzfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Regis : AppCompatActivity() {

    // Firebase Database reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regis)

        val textRegisDisini = findViewById<TextView>(R.id.textRegisDisini)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val editNama = findViewById<EditText>(R.id.editNama)
        val editUsername = findViewById<EditText>(R.id.editUsername)
        val editPassword = findViewById<EditText>(R.id.editPassword)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().reference

        textRegisDisini.setOnClickListener {
            // Navigate to the Login page
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val nama = editNama.text.toString()
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()

            if (nama.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                val userId = database.child("users").push().key ?: "" // Generate unique user ID
                val user = mapOf(
                    "nama" to nama,
                    "username" to username,
                    "password" to password
                )

                // Simpan data user ke database
                database.child("users").child(userId).setValue(user)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()

                        // Kirim userId ke halaman Login
                        val intent = Intent(this, Login::class.java)
                        intent.putExtra("userId", userId) // Kirim userId ke halaman Login
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Registrasi gagal: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
