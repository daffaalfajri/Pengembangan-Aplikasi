package com.example.parentzfinal

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Vaksin : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var userId: String? = null // ID pengguna aktif

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaksin)

        // Ambil userId dari Intent
        userId = intent.getStringExtra("userId")
        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inisialisasi referensi Firebase Database
        database = FirebaseDatabase.getInstance().getReference("anak")

        // Load data anak berdasarkan userId
        loadUserData()

        // Setup RecyclerView untuk daftar vaksin
        setupRecyclerViews()

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        buttonBack.setOnClickListener { finish() }
    }

    private fun loadUserData() {
        if (userId == null) return

        database.child(userId!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nama = snapshot.child("nama").getValue(String::class.java) ?: "Tidak diketahui"
                    val umur = snapshot.child("umur").getValue(String::class.java) ?: "Tidak diketahui"

                    findViewById<TextView>(R.id.tvNamaBayi).text = nama
                    findViewById<TextView>(R.id.tvUmurBayi).text = umur
                } else {
                    Toast.makeText(this@Vaksin, "Data anak tidak ditemukan.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Vaksin, "Gagal memuat data anak.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerViews() {
        val vaksinData = listOf(
            VaksinData("BCG", "Vaksin untuk mencegah tuberkulosis (TB)."),
            VaksinData("Polio", "Vaksin untuk melindungi dari penyakit polio."),
            VaksinData("Hepatitis B", "Vaksin untuk mencegah infeksi hepatitis B."),
            VaksinData("DPT", "Vaksin untuk melindungi dari difteri, pertusis, dan tetanus."),
            VaksinData("Campak", "Vaksin untuk mencegah penyakit campak.")
        )

        // Inisialisasi data vaksin di Firebase
        initializeVaksinData(vaksinData)

        // Setup RecyclerView untuk usia tertentu
        setupRecyclerView(R.id.rvVaksin1, vaksinData, "Lahir")
        setupRecyclerView(R.id.rvVaksin2, vaksinData, "2 Bulan")
        setupRecyclerView(R.id.rvVaksin3, vaksinData, "4 Bulan")
        setupRecyclerView(R.id.rvVaksin4, vaksinData, "1 Tahun")
        setupRecyclerView(R.id.rvVaksin5, vaksinData, "2 Tahun")
        setupRecyclerView(R.id.rvVaksin6, vaksinData, "3 Tahun")

    }

    private fun initializeVaksinData(vaksinData: List<VaksinData>) {
        userId?.let { id ->
            vaksinData.forEach { vaksin ->
                val ageGroups = listOf("Lahir", "2 Bulan", "4 Bulan", "1 Tahun","2 Tahun","3 Tahun")
                ageGroups.forEach { age ->
                    database.child(id).child("vaksin").child(age).child(vaksin.nama)
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (!snapshot.exists()) {
                                // Inisialisasi vaksin dengan nilai default false jika belum ada
                                database.child(id).child("vaksin").child(age).child(vaksin.nama).setValue(false)
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Gagal menginisialisasi data vaksin.", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    private fun setupRecyclerView(rvId: Int, vaksinData: List<VaksinData>, age: String) {
        val recyclerView = findViewById<RecyclerView>(rvId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterVaksin(
            vaksinData = vaksinData,
            age = age,
            userId = userId ?: "" // Pastikan userId tidak null
        )
    }
}
