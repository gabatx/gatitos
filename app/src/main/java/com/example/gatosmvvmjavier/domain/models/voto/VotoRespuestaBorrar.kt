package com.example.gatosmvvmjavier.domain.models.voto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class VotoRespuestaBorrar(
    @SerializedName("message")
    val message: String
) : Parcelable