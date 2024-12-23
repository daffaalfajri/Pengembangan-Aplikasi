package com.example.parentzfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterResepNutrisi(
    private val data: List<Nutrisi.Makanan>,
    private val maxItems: Int = 3, // Jumlah maksimum item yang akan ditampilkan, default 3
    private val onItemClick: ((Nutrisi.Makanan) -> Unit)? = null // Callback untuk item klik
) : RecyclerView.Adapter<AdapterResepNutrisi.ResepNutrisiViewHolder>() {

    // ViewHolder untuk menghubungkan data ke tampilan
    inner class ResepNutrisiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitleResep: TextView = view.findViewById(R.id.tvNamaMakanan)
        val tvDescriptionResep: TextView = view.findViewById(R.id.tvDeskMakan)
        val imgMakanan: ImageView = itemView.findViewById(R.id.imgMakanan)

        // Binding klik pada item
        fun bind(item: Nutrisi.Makanan, onItemClick: ((Nutrisi.Makanan) -> Unit)?) {
            tvTitleResep.text = item.nama
            tvDescriptionResep.text = item.deskripsi
            imgMakanan.setImageResource(item.gambarResId) // Set gambar dari drawable

            // Set onClickListener jika callback disediakan
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    // Fungsi untuk menentukan jumlah item yang ditampilkan
    override fun getItemCount(): Int {
        return if (data.size > maxItems) maxItems else data.size
    }

    // Fungsi untuk membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResepNutrisiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layoutresep, parent, false)
        return ResepNutrisiViewHolder(view)
    }

    // Fungsi untuk menghubungkan data ke ViewHolder
    override fun onBindViewHolder(holder: ResepNutrisiViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onItemClick)
    }
}
