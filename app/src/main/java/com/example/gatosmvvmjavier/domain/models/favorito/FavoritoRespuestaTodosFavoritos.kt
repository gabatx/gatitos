package com.example.gatosmvvmjavier.domain.models.favorito


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class FavoritoRespuestaTodosFavoritos(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image,
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String,
    @SerializedName("user_id")
    val userId: String
) : Parcelable {
    @Parcelize
    data class Image(
        @SerializedName("id")
        val id: String,
        @SerializedName("url")
        val url: String
    ) : Parcelable
}