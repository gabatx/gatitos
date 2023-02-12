package com.example.gatosmvvmjavier.domain.retrofit

import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoEnviarFavorito
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoRespuestaBorrarFavorito
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoRespuestaEnviarFavorito
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoRespuestaTodosFavoritos
import com.example.gatosmvvmjavier.domain.models.voto.VotoEnviar
import com.example.gatosmvvmjavier.domain.models.voto.VotoRespuesta
import com.example.gatosmvvmjavier.domain.models.voto.VotoRespuestaBorrar
import com.example.gatosmvvmjavier.domain.models.voto.VotoTodosRespuesta
import retrofit2.Response
import retrofit2.http.*

const val TOKEN = "ad64113f-48b4-4118-bc42-e4a8475d684c"

interface RetrofitService {
    // RECIBIR TODOS LOS GATOS
    // https://api.thecatapi.com/v1/breeds
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @GET("breeds")
    suspend fun listaTodosGatos() :Response <ArrayList<Gato>>


    // ------- VOTOS --------

    // RECIBIR TODOS LOS VOTOS
    // https://api.thecatapi.com/v1/votes?sub_id=gabatx
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @GET("votes")
    suspend fun listaTodosVotos(@Query("sub_id") sub_id: String) : Response <ArrayList<VotoTodosRespuesta>>

    // ENVIAR UN VOTO
    // https://api.thecatapi.com/v1/votes
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @POST("votes")
    suspend fun enviarVoto(@Body envio: VotoEnviar) : Response <VotoRespuesta>

    // BORRAR UN VOTO
    // https://api.thecatapi.com/v1/votes/{id}
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @DELETE("votes/{id}")
    suspend fun borrarVoto(@Path("id") id: Int) : Response <VotoRespuestaBorrar>


    // ------- FAVORITOS --------

    // RECIBIR FAVORITOS
    // https://api.thecatapi.com/v1/favourites?sub_id=gabatx
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @GET("favourites")
    suspend fun listaTodosFavoritos(@Query("sub_id") sub_id: String) : Response <ArrayList<FavoritoRespuestaTodosFavoritos>>

    // ENVIAR FAVORITO
    // https://api.thecatapi.com/v1/favourites
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @POST("favourites")
    suspend fun enviarFavorito(@Body envio: FavoritoEnviarFavorito) : Response <FavoritoRespuestaEnviarFavorito>

    // BORRAR FAVORITO
    // https://api.thecatapi.com/v1/favourites/{id}
    @Headers("x-api-key:$TOKEN", "Content-Type: application/json")
    @DELETE("favourites/{id}")
    suspend fun borrarFavorito(@Path("id") id: Int) : Response <FavoritoRespuestaBorrarFavorito>
}