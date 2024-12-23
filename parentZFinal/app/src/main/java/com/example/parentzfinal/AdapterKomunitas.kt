package com.example.parentzfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterKomunitas(
    private val context: Context,
    private val judulKomunitas: Array<String>,
    private val deskripsiKomunitas: Array<String>
) : RecyclerView.Adapter<AdapterKomunitas.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgKomunitas: ImageView = view.findViewById(R.id.imageView2)
        val imgAvatar: ImageView = view.findViewById(R.id.imageView3)
        val tvJudulKomunitas: TextView = view.findViewById(R.id.tvJudulKomunitas)
        val tvDeskKomunitas: TextView = view.findViewById(R.id.tvDeskKomunitas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layoutkomunitas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvJudulKomunitas.text = judulKomunitas[position]
        holder.tvDeskKomunitas.text = deskripsiKomunitas[position]
    }

    override fun getItemCount(): Int {
        return judulKomunitas.size
    }
}