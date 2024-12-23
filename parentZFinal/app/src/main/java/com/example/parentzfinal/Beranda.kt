package com.example.parentzfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Beranda : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var userId: String? = null // Variabel untuk menyimpan userId dari login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        //Recycler View keluhan
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
        val recyclerViewKeluhan = findViewById<RecyclerView>(R.id.rvKonsulBeranda)
        recyclerViewKeluhan.layoutManager = LinearLayoutManager(this)
        recyclerViewKeluhan.adapter = AdapterKonsultasi(keluhanList)


        // Ambil data dari strings.xml
        val namaMakanan = resources.getStringArray(R.array.nama_makanan)
        val deskripsiMakanan = resources.getStringArray(R.array.deskripsi_makanan)
        // Data gambar diambil langsung dari kode
        val gambarMakanan = arrayOf(
            R.drawable.imgnasigoreng,
            R.drawable.imgsateayam,
            R.drawable.imgbakso,
            R.drawable.imgmieayam,
            R.drawable.imgnasipadang
        )

// Gabungkan data menjadi List<Makanan>
        val makananList = namaMakanan.indices.map { index ->
            Nutrisi.Makanan(
                nama = namaMakanan[index],
                deskripsi = deskripsiMakanan[index],
                gambarResId = gambarMakanan[index]
            )
        }


        // Inisialisasi RecyclerView
        val recyclerViewResep = findViewById<RecyclerView>(R.id.rvResep)
        recyclerViewResep.layoutManager = LinearLayoutManager(this)
        recyclerViewResep.adapter = AdapterResepNutrisi(makananList)



        // Ambil userId dari intent yang dikirimkan dari Login
        userId = intent.getStringExtra("userId")

        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan, kembali ke login", Toast.LENGTH_SHORT).show()
            finish() // Tutup activity jika userId tidak ada
            return
        }

        // Inisialisasi referensi Firebase Database
        database = FirebaseDatabase.getInstance().getReference("anak")

        // Ambil data anak berdasarkan userId
        fetchDataAnak()

        // Setup tombol navigasi
        findViewById<ImageButton>(R.id.btnPlusPrfl).setOnClickListener {
            val intent = Intent(this, InputData::class.java)
            intent.putExtra("userId", userId) // Kirim userId ke halaman InputData
            startActivity(intent)
        }


        setupNavigationButton(R.id.btnNutrisi, Nutrisi::class.java)
        setupNavigationButton(R.id.btnKonsul, Konsultasi::class.java)
        findViewById<ImageButton>(R.id.btnVaksin).setOnClickListener {
            val intent = Intent(this, Vaksin::class.java)
            intent.putExtra("userId", userId) // Kirim userId ke halaman Vaksin
            startActivity(intent)
        }

        setupNavigationButton(R.id.btnArtikel, ArtikelKomunitas::class.java)
        findViewById<ImageButton>(R.id.btnTracker).setOnClickListener {
            val intent = Intent(this, Tracker::class.java)
            intent.putExtra("userId", userId) // Kirim userId ke halaman Vaksin
            startActivity(intent)
        }

        // Tombol edit anak
        findViewById<TextView>(R.id.tvEdit).setOnClickListener {
            val intent = Intent(this, EditAnak::class.java)
            intent.putExtra("userId", userId) // Kirim userId ke halaman InputData
            startActivity(intent)
        }
    }

    // Fungsi helper untuk navigasi tombol
    private fun <T> setupNavigationButton(buttonId: Int, destination: Class<T>) {
        findViewById<ImageButton>(buttonId).setOnClickListener {
            startActivity(Intent(this, destination))
        }
    }

    // Fungsi untuk mengambil data anak
    private fun fetchDataAnak() {
        userId?.let { id ->
            database.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val nama = snapshot.child("nama").getValue(String::class.java) ?: "Tidak Ada Nama"
                        val umur = snapshot.child("umur").getValue(String::class.java) ?: "Tidak Ada Umur"
                        val tinggi = snapshot.child("tinggi").getValue(String::class.java) ?: "Tidak Ada Tinggi"
                        val berat = snapshot.child("berat").getValue(String::class.java) ?: "Tidak Ada Berat"

                        // Periksa status vaksin
                        checkVaksinStatus()

                        // Update UI dengan data anak
                        updateUI(nama, umur, tinggi, berat)

                        // Ambil data tidur dan makan minum
                        fetchDataTidur()
                        fetchDataMakanMinum()
                    } else {
                        Toast.makeText(this@Beranda, "Data anak tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Beranda", "Gagal mengambil data anak: ${error.message}")
                    Toast.makeText(this@Beranda, "Terjadi kesalahan: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    private fun fetchDataTidur() {
        database.child(userId!!).child("tidur").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val jamTidur = snapshot.child("jamTidur").getValue(String::class.java) ?: "0 Jam"
                    val menitTidur = snapshot.child("menitTidur").getValue(String::class.java) ?: "0 Menit"

                    // Gabungkan jam dan menit tidur ke satu string
                    val durasiTidur = "$jamTidur $menitTidur"

                    // Update UI
                    findViewById<TextView>(R.id.textTidurDurasi).text = durasiTidur
                } else {
                    // Default jika tidak ada data
                    findViewById<TextView>(R.id.textTidurDurasi).text = "0 Jam 0 Menit"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Beranda, "Gagal memuat data tidur", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun fetchDataMakanMinum() {
        database.child(userId!!).child("makanMinum").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val makan = snapshot.child("makan").getValue(String::class.java) ?: "0 kali makan"
                    val minum = snapshot.child("minum").getValue(String::class.java) ?: "0 kali minum"

                    // Update UI
                    findViewById<TextView>(R.id.textMakan).text = makan
                    findViewById<TextView>(R.id.textMinum).text = minum
                } else {
                    // Default jika tidak ada data
                    findViewById<TextView>(R.id.textMakan).text = "0 kali makan"
                    findViewById<TextView>(R.id.textMinum).text = "0 kali minum"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Beranda, "Gagal memuat data makan dan minum", Toast.LENGTH_SHORT).show()
            }
        })
    }




    private fun checkVaksinStatus() {
        if (userId == null) return

        database.child(userId!!).child("vaksin").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // Jika tidak ada data vaksin, set status ke "Belum"
                    findViewById<TextView>(R.id.statusvaksin).text = "Belum"
                    return
                }

                var allComplete = false // Asumsi awal tidak selesai

                // Iterasi melalui semua usia (3 Tahun, 4 Bulan, dll.)
                for (ageSnapshot in snapshot.children) {
                    var ageComplete = true // Asumsi vaksin untuk satu kelompok umur selesai

                    // Periksa setiap vaksin di dalam usia
                    for (vaksinSnapshot in ageSnapshot.children) {
                        val isComplete = vaksinSnapshot.getValue(Boolean::class.java) ?: false
                        if (!isComplete) {
                            ageComplete = false // Jika ada vaksin belum selesai, ubah status untuk usia ini
                            break
                        }
                    }

                    if (ageComplete) {
                        // Jika semua vaksin dalam satu kelompok umur selesai, set status ke "Selesai"
                        allComplete = true
                        break // Tidak perlu memeriksa usia lain
                    }
                }

                val status = if (allComplete) "Selesai" else "Belum"
                findViewById<TextView>(R.id.statusvaksin).text = status
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Beranda, "Gagal memuat status vaksin.", Toast.LENGTH_SHORT).show()
            }
        })
    }





    // Fungsi untuk mengupdate UI dengan data anak
    private fun updateUI(nama: String, umur: String, tinggi: String, berat: String) {
        findViewById<TextView>(R.id.textNameBaby).text = nama
        findViewById<TextView>(R.id.textMonthBaby).text = umur
        findViewById<TextView>(R.id.tinggibadan).text = tinggi
        findViewById<TextView>(R.id.beratbadan).text = berat
    }
}
