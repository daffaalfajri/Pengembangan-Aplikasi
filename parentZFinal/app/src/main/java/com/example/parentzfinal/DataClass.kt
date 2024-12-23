package com.example.parentzfinal

// Data untuk anak
data class DataClass(
    val nama: String = "",
    val umur: String = "",
    val tinggi: String = "",
    val berat: String = ""
)

// Data untuk makanan
data class Makanan(
    val nama: String,
    val deskripsi: String,
    val gambarResId: String // ID dari gambar drawable
)
data class VaksinData(
    val nama: String,
    val deskripsi: String,
    var isChecked: Boolean = false // Status apakah sudah divaksin
)
