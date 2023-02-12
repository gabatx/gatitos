package com.example.gatosmvvmjavier

import android.app.Application
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.domain.retrofit.Repositorio


class AppGatos: Application() {

    val repositorio by lazy { Repositorio()}

    private var listaGatos: ArrayList<Gato>? = null
    fun getGatos(): ArrayList<Gato>? = listaGatos
    fun setGatos(lista: ArrayList<Gato>?) {
        listaGatos = lista
    }
}