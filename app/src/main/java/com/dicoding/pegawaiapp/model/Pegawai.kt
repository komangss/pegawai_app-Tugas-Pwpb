package com.dicoding.pegawaiapp.model

import com.google.gson.annotations.SerializedName

data class Pegawai (
    val id : Int?,
    @SerializedName("name")
    val nama : String?,
    @SerializedName("desg")
    val posisi : String?,
    @SerializedName("salary")
    val gaji : Int?
)
