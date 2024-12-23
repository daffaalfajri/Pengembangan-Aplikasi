package com.example.parentzfinal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterArtikel (
    private val context: Context,
    private val judulArtikel: Array<String>,
    private val sumberArtikel: Array<String>
) : RecyclerView.Adapter<AdapterArtikel.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgArtikel: ImageView = view.findViewById(R.id.imgArtikel)
        val tvJudulArtikel: TextView = view.findViewById(R.id.tvJudulArtikel)
        val tvLinkArtikel: TextView = view.findViewById(R.id.tvLinkArtikel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layoutartikel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvJudulArtikel.text = judulArtikel[position]
        holder.tvLinkArtikel.text = sumberArtikel[position]
    }

    override fun getItemCount(): Int {
        return judulArtikel.size
    }
}