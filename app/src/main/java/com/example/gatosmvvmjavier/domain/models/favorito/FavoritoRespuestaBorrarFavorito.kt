package com.example.gatosmvvmjavier.domain.models.favorito


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class FavoritoRespuestaBorrarFavorito(
    @SerializedName("message")
    val message: String
) : Parcelable