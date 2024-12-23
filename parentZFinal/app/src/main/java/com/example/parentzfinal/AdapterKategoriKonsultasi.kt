package com.example.parentzfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterKategoriKonsultasi (
    private val context: Context,
    private val namaKeluhan: Array<String>,
    private val deskripsiKeluhan: Array<String>
) : RecyclerView.Adapter<AdapterKategoriKonsultasi.KategoriViewHolder>() {

    inner class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaKeluhan: TextView = itemView.findViewById(R.id.tvJudulKeluhan)
        val tvDeskripsiKeluhan: TextView = itemView.findViewById(R.id.tvDeskKeluhan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layoutkonsultasi, parent, false)
        return KategoriViewHolder(view)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        holder.tvNamaKeluhan.text = namaKeluhan[position]
        holder.tvDeskripsiKeluhan.text = deskripsiKeluhan[position]
    }

    override fun getItemCount(): Int = namaKeluhan.size

}
