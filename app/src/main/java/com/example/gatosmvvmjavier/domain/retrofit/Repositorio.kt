package com.example.gatosmvvmjavier.domain.retrofit

import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoEnviarFavorito
import com.example.gatosmvvmjavier.domain.models.voto.VotoEnviar

class Repositorio {

    private val retrofitService = RetrofitHelper.getRetrofit()
    private val retrofitServiceVotoEnviar = RetrofitHelper.getRetrofit()
    private val retrofitServiceVotoTodos = RetrofitHelper.getRetrofit()
    private val retrofitServiceBorrarVoto = RetrofitHelper.getRetrofit()

    private val retrofitServiceFavoritoEnviar = RetrofitHelper.getRetrofit()
    private val retrofitServiceFavoritoTodos = RetrofitHelper.getRetrofit()
    private val retrofitServiceBorrarFavorito = RetrofitHelper.getRetrofit()

    // TODOS LOS GATOS
    suspend fun listaTodosGatos() = retrofitService.listaTodosGatos()

    // VOTOS
    suspend fun listaTodosVotos(sub_id: String) = retrofitServiceVotoTodos.listaTodosVotos(sub_id)
    suspend fun enviarVoto(voto: VotoEnviar) = retrofitServiceVotoEnviar.enviarVoto(voto)
    suspend fun borrarVoto(id_voto:Int) = retrofitServiceBorrarVoto.borrarVoto(id_voto)

    // FAVORITOS
    suspend fun listaTodosFavoritos(sub_id: String) = retrofitServiceFavoritoTodos.listaTodosFavoritos(sub_id)
    suspend fun enviarFavorito(favorito: FavoritoEnviarFavorito) = retrofitServiceFavoritoEnviar.enviarFavorito(favorito)
    suspend fun borrarFavorito(id_favorito:Int) = retrofitServiceBorrarFavorito.borrarFavorito(id_favorito)
}
