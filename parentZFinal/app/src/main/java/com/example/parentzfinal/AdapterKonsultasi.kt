package com.example.parentzfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterKonsultasi(private val keluhanList: List<Keluhan>) :
    RecyclerView.Adapter<AdapterKonsultasi.KomunitasViewHolder>() {

    inner class KomunitasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudulKeluhan: TextView = itemView.findViewById(R.id.tvJudulKeluhan)
        val tvDeskKeluhan: TextView = itemView.findViewById(R.id.tvDeskKeluhan)
        val imgKeluhan: ImageView = itemView.findViewById(R.id.imgPrflKeluhan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomunitasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layoutkonsultasi, parent, false)
        return KomunitasViewHolder(view)
    }

    override fun onBindViewHolder(holder: KomunitasViewHolder, position: Int) {
        val keluhan = keluhanList[position]
        holder.tvJudulKeluhan.text = keluhan.nama
        holder.tvDeskKeluhan.text = keluhan.deskripsi
        holder.imgKeluhan.setImageResource(keluhan.gambarResId) // Set gambar dari drawable
    }

    override fun getItemCount(): Int {
        return if (keluhanList.size > 3) 3 else keluhanList.size
    }
}
