package com.example.gatosmvvmjavier.domain.models.voto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class VotoRespuesta(
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String
) : Parcelable