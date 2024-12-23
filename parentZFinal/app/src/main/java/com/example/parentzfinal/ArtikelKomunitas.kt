package com.example.parentzfinal

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArtikelKomunitas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artikel_komunitas)

        // Tombol Back
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya (Beranda.kt)
        }


        val rvArtikel = findViewById<RecyclerView>(R.id.rvArtikel)
        val rvKomunitas = findViewById<RecyclerView>(R.id.rvKomunitas)
        val judulArtikel = resources.getStringArray(R.array.judul_artikel)
        val sumberArtikel = resources.getStringArray(R.array.sumber_artikel)

        val adapterArtikel = AdapterArtikel(this, judulArtikel, sumberArtikel)
        rvArtikel.layoutManager = LinearLayoutManager(this)
        rvArtikel.adapter = adapterArtikel

        val judulKomunitas = resources.getStringArray(R.array.judul_komunitas)
        val deskripsiKomunitas = resources.getStringArray(R.array.deskripsi_komunitas)

        val adapterKomunitas = AdapterKomunitas(this, judulKomunitas, deskripsiKomunitas)
        rvKomunitas.layoutManager = LinearLayoutManager(this)
        rvKomunitas.adapter = adapterKomunitas






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}