package com.example.parentzfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Tidur : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tidur_activity)

        database = FirebaseDatabase.getInstance().getReference("anak")
        userId = intent.getStringExtra("userId")

        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val inputTanggal = findViewById<EditText>(R.id.inputTanggalTidur)
        val inputJam = findViewById<EditText>(R.id.inputJamTidur)
        val inputMenit = findViewById<EditText>(R.id.inputMenitTidur)
        val btnInput = findViewById<Button>(R.id.btnInputTidur)

        btnInput.setOnClickListener {
            val tanggal = inputTanggal.text.toString()
            val jam = inputJam.text.toString()
            val menit = inputMenit.text.toString()

            if (tanggal.isEmpty() || jam.isEmpty() || menit.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val data = mapOf(
                    "tanggal" to tanggal,
                    "jamTidur" to "$jam jam",
                    "menitTidur" to "$menit menit"
                )

                database.child(userId!!).child("tidur").setValue(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
