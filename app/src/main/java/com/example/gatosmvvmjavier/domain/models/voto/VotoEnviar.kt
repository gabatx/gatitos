package com.example.gatosmvvmjavier.domain.models.voto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class VotoEnviar(
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String,
    @SerializedName("value")
    val value: Int
) : Parcelable