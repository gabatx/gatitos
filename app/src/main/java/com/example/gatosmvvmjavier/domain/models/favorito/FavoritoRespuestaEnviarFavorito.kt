package com.example.gatosmvvmjavier.domain.models.favorito


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class FavoritoRespuestaEnviarFavorito(
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String
) : Parcelable