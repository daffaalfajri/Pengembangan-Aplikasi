package com.example.parentzfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterResep(private val resepList: List<Nutrisi.Makanan>) :
    RecyclerView.Adapter<AdapterResep.ResepViewHolder>() {

    inner class ResepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaMakanan: TextView = itemView.findViewById(R.id.tvNamaMakanan)
        val tvDeskMakan: TextView = itemView.findViewById(R.id.tvDeskMakan)
        val imgMakanan: ImageView = itemView.findViewById(R.id.imgMakanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResepViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layoutresep, parent, false)
        return ResepViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResepViewHolder, position: Int) {
        val makanan = resepList[position]
        holder.tvNamaMakanan.text = makanan.nama
        holder.tvDeskMakan.text = makanan.deskripsi
        holder.imgMakanan.setImageResource(makanan.gambarResId)
    }

    override fun getItemCount(): Int = resepList.size
}