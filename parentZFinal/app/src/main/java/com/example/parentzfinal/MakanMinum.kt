package com.example.parentzfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MakanMinum : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.makanminum_activity)

        database = FirebaseDatabase.getInstance().getReference("anak")
        userId = intent.getStringExtra("userId")

        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val inputTanggal = findViewById<EditText>(R.id.inputTanggalMakan)
        val inputMakan = findViewById<EditText>(R.id.inputMakan)
        val inputMinum = findViewById<EditText>(R.id.inputMinum)
        val btnInput = findViewById<Button>(R.id.btnInputMakanMinum)

        btnInput.setOnClickListener {
            val tanggal = inputTanggal.text.toString()
            val makan = inputMakan.text.toString()
            val minum = inputMinum.text.toString()

            if (tanggal.isEmpty() || makan.isEmpty() || minum.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val data = mapOf(
                    "tanggal" to tanggal,
                    "makan" to "$makan kali makan",
                    "minum" to "$minum kali menyusui"
                )

                database.child(userId!!).child("makanMinum").setValue(data)
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