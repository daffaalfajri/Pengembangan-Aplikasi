package com.example.parentzfinal

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KategoriKonsultasi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kategori_konsultasi)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya (Beranda.kt)
        }

        val rvKategoriKonsultasi: RecyclerView = findViewById(R.id.rvKategoriKonsultasi)

        // Ambil data dari string.xml
        val namaKeluhan = resources.getStringArray(R.array.nama_keluhan)
        val deskripsiKeluhan = resources.getStringArray(R.array.deskripsi_keluhan)

        // Set adapter dan layout manager
        rvKategoriKonsultasi.layoutManager = LinearLayoutManager(this)
        rvKategoriKonsultasi.adapter = AdapterKategoriKonsultasi(this, namaKeluhan, deskripsiKeluhan)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}