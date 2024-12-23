package com.example.parentzfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdapterVaksin(
    private val vaksinData: List<VaksinData>,
    private val age: String,
    private val userId: String
) : RecyclerView.Adapter<AdapterVaksin.VaksinViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("anak")

    inner class VaksinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaVaksin: TextView = itemView.findViewById(R.id.tvNamaVaksin)
        val tvDeskVaksin: TextView = itemView.findViewById(R.id.tvDeskVaksin)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaksinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layoutvaksin, parent, false)
        return VaksinViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaksinViewHolder, position: Int) {
        val vaksin = vaksinData[position]
        holder.tvNamaVaksin.text = vaksin.nama
        holder.tvDeskVaksin.text = vaksin.deskripsi

        holder.checkBox.setOnCheckedChangeListener(null)

        // Load initial status
        database.child(userId).child("vaksin").child(age).child(vaksin.nama)
            .get()
            .addOnSuccessListener { snapshot ->
                val isChecked = snapshot.getValue(Boolean::class.java) ?: false
                holder.checkBox.isChecked = isChecked
            }

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            database.child(userId).child("vaksin").child(age).child(vaksin.nama)
                .setValue(isChecked)
                .addOnSuccessListener {
                    Toast.makeText(
                        holder.itemView.context,
                        "${vaksin.nama} diperbarui: ${if (isChecked) "Selesai" else "Belum"}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Periksa apakah semua vaksin dalam kelompok umur selesai
                    checkAllVaccinesComplete(holder)
                }
                .addOnFailureListener {
                    Toast.makeText(
                        holder.itemView.context,
                        "Gagal memperbarui status vaksin ${vaksin.nama}.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun checkAllVaccinesComplete(holder: VaksinViewHolder) {
        database.child(userId).child("vaksin").child(age)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    var allComplete = true
                    for (vaksinSnapshot in snapshot.children) {
                        val isComplete = vaksinSnapshot.getValue(Boolean::class.java) ?: false
                        if (!isComplete) {
                            allComplete = false
                            break
                        }
                    }

                    if (allComplete) {
                        // Semua vaksin dalam kelompok umur selesai
                        Toast.makeText(
                            holder.itemView.context,
                            "Semua vaksin untuk $age selesai!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    override fun getItemCount(): Int = vaksinData.size
}

