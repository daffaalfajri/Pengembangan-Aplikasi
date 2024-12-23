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
import com.google.firebase.database.*

class Login : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inisialisasi elemen UI
        val editUsername = findViewById<EditText>(R.id.username)
        val editPassword = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        findViewById<TextView>(R.id.textRegisDisini).setOnClickListener {
            startActivity(Intent(this, Regis::class.java))
        }

        // Inisialisasi referensi database
        database = FirebaseDatabase.getInstance().getReference("users")

        btnLogin.setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Cari user berdasarkan username
                database.orderByChild("username").equalTo(username)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (userSnapshot in snapshot.children) {
                                    val storedPassword = userSnapshot.child("password").getValue(String::class.java)
                                    if (storedPassword == password) {
                                        // Login berhasil, ambil userId dan lanjutkan ke halaman berikutnya
                                        val userId = userSnapshot.key ?: ""
                                        val intent = Intent(this@Login, Beranda::class.java)
                                        intent.putExtra("userId", userId) // Kirim userId ke halaman berikutnya
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this@Login, "Password salah", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this@Login, "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@Login, "Gagal login: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Username dan password harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        // Atur padding untuk layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
