package com.example.parentzfinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AktivitasFisik : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aktivitasfisik)

        database = FirebaseDatabase.getInstance().getReference("anak")
        userId = intent.getStringExtra("userId")

        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val inputAktivitas = findViewById<EditText>(R.id.inputAktivitasFisik)
        val btnInput = findViewById<Button>(R.id.btnInputAktivitasFisik)

        btnInput.setOnClickListener {
            val aktivitas = inputAktivitas.text.toString()

            if (aktivitas.isEmpty()) {
                Toast.makeText(this, "Field aktivitas tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                val data = mapOf("aktivitas" to aktivitas)

                database.child(userId!!).child("aktivitasFisik").setValue(data)
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
