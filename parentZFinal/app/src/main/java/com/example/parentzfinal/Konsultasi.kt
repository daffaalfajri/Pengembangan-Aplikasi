package com.example.parentzfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Konsultasi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_konsultasi)

        // Tombol Back
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }

        val buttonAllQuestion = findViewById<Button>(R.id.btnAllQuestion)
        buttonAllQuestion.setOnClickListener {
            val intent = Intent(this, KategoriKonsultasi::class.java)
            startActivity(intent)
        }

        // RecyclerView keluhan
        val namaKeluhan = resources.getStringArray(R.array.nama_keluhan)
        val deskripsiKeluhan = resources.getStringArray(R.array.deskripsi_keluhan)
        val gambarKeluhan = listOf(
            R.drawable.imgcantas,
            R.drawable.imgkiting,
            R.drawable.imgrifqi,
            R.drawable.imgkitinglagi,
            R.drawable.imgrizal
        )

        // Gabungkan data menjadi List<Keluhan>
        val keluhanList = namaKeluhan.indices.map { index ->
            Keluhan(namaKeluhan[index], deskripsiKeluhan[index],
                gambarResId = gambarKeluhan[index])
        }

        // Inisialisasi RecyclerView
        val recyclerViewKeluhan = findViewById<RecyclerView>(R.id.rvKonsultasi)
        recyclerViewKeluhan.layoutManager = LinearLayoutManager(this)
        recyclerViewKeluhan.adapter = AdapterKonsultasi(keluhanList)

        // Hubungkan TextView
        val tvCekProfil1 = findViewById<TextView>(R.id.tvCekProfil1)
        val tvCekProfil2 = findViewById<TextView>(R.id.tvCekProfil2)
        val tvCekProfil3 = findViewById<TextView>(R.id.tvCekProfil3)

        // Tambahkan listener klik
        tvCekProfil1.setOnClickListener {
            val bottomSheet = DoctorBottomSheet()
            bottomSheet.show(supportFragmentManager, "DoctorDetailBottomSheet")
        }
        tvCekProfil2.setOnClickListener {
            val bottomSheet = DoctorBottomSheet()
            bottomSheet.show(supportFragmentManager, "DoctorDetailBottomSheet")
        }
        tvCekProfil3.setOnClickListener {
            val bottomSheet = DoctorBottomSheet()
            bottomSheet.show(supportFragmentManager, "DoctorDetailBottomSheet")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
