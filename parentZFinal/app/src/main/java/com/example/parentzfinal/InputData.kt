package com.example.parentzfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import android.widget.ImageButton

class InputData : AppCompatActivity() {

    // Variabel untuk Firebase Realtime Database
    private lateinit var database: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        // Tombol Back
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya (Beranda.kt)
        }

        // Ambil `userId` dari Intent
        userId = intent.getStringExtra("userId")
        if (userId == null) {
            Toast.makeText(this, "Terjadi kesalahan: User tidak ditemukan!", Toast.LENGTH_SHORT).show()
            finish() // Tutup activity jika `userId` tidak ada
            return
        }

        // Log untuk memastikan userId diterima
        Log.d("InputData", "User ID diterima: $userId")

        // Inisialisasi Firebase Database
        database = FirebaseDatabase.getInstance().getReference("anak")

        // Inisialisasi View
        val editNama = findViewById<EditText>(R.id.editTextText2)
        val editUmur = findViewById<EditText>(R.id.editTextText3)
        val editTinggi = findViewById<EditText>(R.id.editTextText4)
        val editBerat = findViewById<EditText>(R.id.editTextText5)
        val buttonInput = findViewById<Button>(R.id.button2)

        // Event Listener untuk tombol "Input Data"
        buttonInput.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val umur = editUmur.text.toString().trim()
            val tinggi = editTinggi.text.toString().trim()
            val berat = editBerat.text.toString().trim()

            // Validasi input data
            if (nama.isEmpty() || umur.isEmpty() || tinggi.isEmpty() || berat.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan data ke Firebase
                saveDataToFirebase(nama, umur, tinggi, berat)
            }
        }
    }

    private fun saveDataToFirebase(nama: String, umur: String, tinggi: String, berat: String) {
        // Periksa apakah `userId` tersedia
        if (userId != null) {
            // Buat struktur data anak
            val anak = mapOf(
                "nama" to nama,
                "umur" to umur,
                "tinggi" to tinggi,
                "berat" to berat
            )

            // Simpan data anak ke Firebase
            database.child(userId!!).setValue(anak)
                .addOnSuccessListener {
                    // Log untuk debugging
                    Log.d("InputData", "Data berhasil disimpan untuk userId: $userId")

                    // Tampilkan notifikasi sukses
                    Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()

                    // Bersihkan input setelah berhasil disimpan
                    clearInputs()

                    // Kembali ke halaman Beranda
                    navigateToBeranda()
                }
                .addOnFailureListener {
                    // Log error jika gagal
                    Log.e("InputData", "Gagal menyimpan data: ${it.message}")
                    Toast.makeText(this, "Gagal menyimpan data: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Terjadi kesalahan, coba lagi!", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk membersihkan input
    private fun clearInputs() {
        findViewById<EditText>(R.id.editTextText2).text.clear()
        findViewById<EditText>(R.id.editTextText3).text.clear()
        findViewById<EditText>(R.id.editTextText4).text.clear()
        findViewById<EditText>(R.id.editTextText5).text.clear()
    }

    // Fungsi untuk navigasi ke Beranda
    private fun navigateToBeranda() {
        val intent = Intent(this, Beranda::class.java)
        intent.putExtra("userId", userId) // Kirim userId ke Beranda
        startActivity(intent)
        finish()
    }
}
