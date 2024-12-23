package com.example.parentzfinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Nutrisi : AppCompatActivity() {

    data class Makanan(
        val nama: String,
        val deskripsi: String,
        val gambarResId: Int // ID dari gambar drawable
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nutrisi)


        // Tombol Back
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya (Beranda.kt)
        }


        // Referensi ke ImageButton untuk kategori
        val btnBiji: ImageButton = findViewById(R.id.btnBiji)
        btnBiji.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Biji-bijian")
            startActivity(intent)
        }

        val btnMknnOlah: ImageButton = findViewById(R.id.btnMknnOlah)
        btnMknnOlah.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Makanan Olahan")
            startActivity(intent)
        }

        val btnMknnRingan: ImageButton = findViewById(R.id.btnMknnRingan)
        btnMknnRingan.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Makanan Ringan")
            startActivity(intent)
        }

        val btnSayuran: ImageButton = findViewById(R.id.btnSayuran)
        btnSayuran.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Sayuran & Jamur")
            startActivity(intent)
        }

        val btnBumbu: ImageButton = findViewById(R.id.btnBumbu)
        btnBumbu.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Bumbu Makanan")
            startActivity(intent)
        }

        val btnMknnLaut: ImageButton = findViewById(R.id.btnMknnLaut)
        btnMknnLaut.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Makanan Laut")
            startActivity(intent)
        }

        val btnKacang: ImageButton = findViewById(R.id.btnKacang)
        btnKacang.setOnClickListener{
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Kacang-kacangan")
            startActivity(intent)
        }

        val btnSuplemen: ImageButton = findViewById(R.id.btnSuplemen)
        btnSuplemen.setOnClickListener{
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Suplemen & Obat-obatan")
            startActivity(intent)
        }

        val btnMinuman: ImageButton = findViewById(R.id.btnMinuman)
        btnMinuman.setOnClickListener{
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Minuman")
            startActivity(intent)
        }

        val btnOlahKacang: ImageButton = findViewById(R.id.btnOlahKacang)
        btnOlahKacang.setOnClickListener{
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Olahan Kacang Susu")
            startActivity(intent)
        }

        val btnBuah: ImageButton = findViewById(R.id.btnBuah)
        btnBuah.setOnClickListener{
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Buah-buahan")
            startActivity(intent)
        }

        val btnDaging: ImageButton = findViewById(R.id.btnDaging)
        btnDaging.setOnClickListener{
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Daging & Protein")
            startActivity(intent)
        }

        val btnSemua: TextView = findViewById(R.id.btnSemua)
        btnSemua.setOnClickListener {
            val intent = Intent(this, KategoriNutrisi::class.java)
            intent.putExtra("CATEGORY", "Resep")
            startActivity(intent)
        }



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
            Makanan(namaMakanan[index], deskripsiMakanan[index], gambarResId = gambarMakanan[index])
        }
        // Setup RecyclerView
        val rvResepNutrisi: RecyclerView = findViewById(R.id.rvResepNutrisi)
        rvResepNutrisi.layoutManager = LinearLayoutManager(this)
        rvResepNutrisi.adapter = AdapterResepNutrisi(makananList)














        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}