package com.example.parentzfinal

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KategoriNutrisi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kategori_nutrisi)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya (Beranda.kt)
        }

        // Ambil data dari strings.xml
        val titles = resources.getStringArray(R.array.nutrisi_titles).toList()
        val descriptions = resources.getStringArray(R.array.nutrisi_descriptions).toList()

        // Referensi RecyclerView
        val rvKategoriNutrisi = findViewById<RecyclerView>(R.id.rvKategoriNutrisi)

        // Setup RecyclerView
        rvKategoriNutrisi.layoutManager = LinearLayoutManager(this)
        rvKategoriNutrisi.adapter = AdapterKategoriNutrisi(titles, descriptions)

        val category = intent.getStringExtra("CATEGORY")
        findViewById<TextView>(R.id.tvKategoriNutrisi).text = category


        // Cek apakah kategori adalah "Resep"
        if (category == "Resep") {
            // Ambil data dari strings.xml
            val namaMakanan = resources.getStringArray(R.array.nama_makanan)
            val deskripsiMakanan = resources.getStringArray(R.array.deskripsi_makanan)
            val gambarMakanan = arrayOf(
                R.drawable.imgnasigoreng,
                R.drawable.imgsateayam,
                R.drawable.imgbakso,
                R.drawable.imgmieayam,
                R.drawable.imgnasipadang
            )

            // Gabungkan data menjadi List<Makanan>
            val makananList = namaMakanan.indices.map { index ->
                Nutrisi.Makanan(namaMakanan[index], deskripsiMakanan[index], gambarResId = gambarMakanan[index])
            }

            // Setup RecyclerView
            val rvResep: RecyclerView = findViewById(R.id.rvKategoriNutrisi)
            rvResep.layoutManager = LinearLayoutManager(this)
            rvResep.adapter = AdapterResep(makananList)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}