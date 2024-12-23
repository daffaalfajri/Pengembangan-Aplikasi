package com.example.parentzfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditAnak : AppCompatActivity() {

    private var userId: String? = null // ID pengguna yang sedang login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_anak)

        val inputUmur = findViewById<EditText>(R.id.inputUmurAnak)
        val inputTinggi = findViewById<EditText>(R.id.inputTinggiAnak)
        val inputBerat = findViewById<EditText>(R.id.inputBeratAnak)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Ambil userId dari Intent
        userId = intent.getStringExtra("userId")
        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val database = FirebaseDatabase.getInstance().getReference("anak")

        // Load data awal untuk ditampilkan di input
        loadData(database, inputUmur, inputTinggi, inputBerat)

        btnSave.setOnClickListener {
            val umur = inputUmur.text.toString()
            val tinggi = inputTinggi.text.toString()
            val berat = inputBerat.text.toString()

            if (umur.isEmpty() || tinggi.isEmpty() || berat.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val data = mapOf(
                    "umur" to umur,
                    "tinggi" to tinggi,
                    "berat" to berat
                )
                database.child(userId!!).updateChildren(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        finish() // Kembali ke halaman sebelumnya
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Mengatur padding untuk edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadData(
        database: DatabaseReference,
        inputUmur: EditText,
        inputTinggi: EditText,
        inputBerat: EditText
    ) {
        userId?.let { id ->
            database.child(id).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val umur = snapshot.child("umur").getValue(String::class.java) ?: ""
                    val tinggi = snapshot.child("tinggi").getValue(String::class.java) ?: ""
                    val berat = snapshot.child("berat").getValue(String::class.java) ?: ""

                    inputUmur.setText(umur)
                    inputTinggi.setText(tinggi)
                    inputBerat.setText(berat)
                } else {
                    Toast.makeText(this, "Data anak tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal memuat data anak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
