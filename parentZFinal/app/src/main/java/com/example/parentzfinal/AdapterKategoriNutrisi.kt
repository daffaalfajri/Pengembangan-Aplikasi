package com.example.parentzfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterKategoriNutrisi (
    private val titles: List<String>,
    private val descriptions: List<String>
) : RecyclerView.Adapter<AdapterKategoriNutrisi.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaNutrisi: TextView = view.findViewById(R.id.tvNamaNutrisi)
        val tvDeskNutrisi: TextView = view.findViewById(R.id.tvDeskNutrisi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layoutrvkatnutrisi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNamaNutrisi.text = titles[position]
        holder.tvDeskNutrisi.text = descriptions[position]
    }

    override fun getItemCount(): Int = titles.size

    }