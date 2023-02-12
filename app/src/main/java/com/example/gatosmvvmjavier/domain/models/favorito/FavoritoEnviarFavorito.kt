package com.example.gatosmvvmjavier.domain.models.favorito


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class FavoritoEnviarFavorito(
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String
) : Parcelable