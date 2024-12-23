package com.example.parentzfinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.*

class Tracker : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var userId: String? = null // ID pengguna aktif

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tracker)

        // Tombol Back
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya (Beranda.kt)
        }

        // Ambil userId dari Intent
        userId = intent.getStringExtra("userId")
        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inisialisasi referensi Firebase Database
        database = FirebaseDatabase.getInstance().getReference("anak").child(userId!!)

        // Navigasi ke halaman input
        findViewById<ImageView>(R.id.imgTidur).setOnClickListener {
            startActivity(Intent(this, Tidur::class.java).putExtra("userId", userId))
        }

        findViewById<ImageView>(R.id.imgMakanMinum).setOnClickListener {
            startActivity(Intent(this, MakanMinum::class.java).putExtra("userId", userId))
        }

        findViewById<ImageView>(R.id.imgBuangAir).setOnClickListener {
            startActivity(Intent(this, AktivitasFisik::class.java).putExtra("userId", userId))
        }

        // Ambil data dari Firebase dan tampilkan di UI
        fetchAndDisplayData()

        // Atur padding untuk edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchAndDisplayData() {
        // Ambil data anak (Nama, Umur)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nama = snapshot.child("nama").getValue(String::class.java) ?: "Tidak ada data"
                val umur = snapshot.child("umur").getValue(String::class.java) ?: "Tidak ada data"

                findViewById<TextView>(R.id.tvNamaBayi).text = nama
                findViewById<TextView>(R.id.tvUmurBayi).text = umur
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Tracker, "Gagal memuat data anak", Toast.LENGTH_SHORT).show()
            }
        })

        // Tampilkan data untuk "Tidur"
        database.child("tidur").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tanggal = snapshot.child("tanggal").getValue(String::class.java) ?: "Tidak ada data"
                val jamTidur = snapshot.child("jamTidur").getValue(String::class.java) ?: "Tidak ada data"
                val menitTidur = snapshot.child("menitTidur").getValue(String::class.java) ?: "Tidak ada data"

                findViewById<TextView>(R.id.tvTanggalTidur).text = tanggal
                findViewById<TextView>(R.id.tvJamTidur).text = jamTidur
                findViewById<TextView>(R.id.tvMenitTidur).text = menitTidur
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Tracker, "Gagal memuat data tidur", Toast.LENGTH_SHORT).show()
            }
        })

        // Tampilkan data untuk "Makan dan Minum"
        database.child("makanMinum").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tanggal = snapshot.child("tanggal").getValue(String::class.java) ?: "Tidak ada data"
                val makan = snapshot.child("makan").getValue(String::class.java) ?: "Tidak ada data"
                val minum = snapshot.child("minum").getValue(String::class.java) ?: "Tidak ada data"

                findViewById<TextView>(R.id.tvTanggalMakan).text = tanggal
                findViewById<TextView>(R.id.textMakan).text = makan
                findViewById<TextView>(R.id.textMinum).text = minum
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Tracker, "Gagal memuat data makan dan minum", Toast.LENGTH_SHORT).show()
            }
        })

        // Tampilkan data untuk "Aktivitas Fisik"
        database.child("aktivitasFisik").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val aktivitas = snapshot.child("aktivitas").getValue(String::class.java) ?: "Tidak ada data"

                findViewById<TextView>(R.id.tvAktivitasFisik).text = aktivitas
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Tracker, "Gagal memuat data aktivitas fisik", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
