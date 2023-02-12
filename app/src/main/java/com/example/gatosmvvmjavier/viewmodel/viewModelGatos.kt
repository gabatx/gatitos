package com.example.gatosmvvmjavier.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoEnviarFavorito
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoRespuestaTodosFavoritos
import com.example.gatosmvvmjavier.domain.models.voto.VotoEnviar
import com.example.gatosmvvmjavier.domain.models.voto.VotoTodosRespuesta
import com.example.gatosmvvmjavier.domain.retrofit.Repositorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelGatos : ViewModel() {

    val repositorio = Repositorio()

    val listaTodosGatos = MutableLiveData<ArrayList<Gato>>()
    val listaVotosGatos = MutableLiveData<ArrayList<VotoTodosRespuesta>>()
    val listaFavoritosGatos = MutableLiveData<ArrayList<FavoritoRespuestaTodosFavoritos>>()
    val mostrarSimboloCarga = MutableLiveData<Boolean>()
    val errorRazas = MutableLiveData<String>()
    val correctoRazas = MutableLiveData<String>()
    val gatoSeleccionado = MutableLiveData<Gato>()
    val gatoVotado = MutableLiveData<Boolean>()
    val gatoFavorito = MutableLiveData<Boolean>()

    //  ---------- ---------- ----------
    // ----------  TODOS LOS GATOS  ----
    //  ---------- ---------- ----------

    fun listaTodosGatos() {
        mostrarSimboloCarga.value = true // Muestra el circulo de carga mientras hace la llamada
        CoroutineScope(Dispatchers.IO).launch {
            val respuesta = repositorio.listaTodosGatos()

            if (respuesta.isSuccessful && respuesta.code() == 200) {
                respuesta.body().let {
                    listaTodosGatos.postValue(it)
                }
            } else {
                errorRazas.postValue(respuesta.message())
            }
            mostrarSimboloCarga.postValue(false) // Borra el circulo de carga una vez que se ha realizado la llamada
        }
    }

    //  ---------- ---------- ----------
    // ----------  VOTOS --------------
    //  ---------- ---------- ----------


    // Comprueba si el gato ya ha votado y cambia el color del icono
    fun listaVotosGatos() {
        CoroutineScope(Dispatchers.IO).launch {
            val respuestaListaVotos = repositorio.listaTodosVotos("gabatx")

            withContext(Dispatchers.Main) {
                if (respuestaListaVotos.isSuccessful && respuestaListaVotos.code() == 200) {

                    respuestaListaVotos.body().let {
                        listaVotosGatos.postValue(it)
                    }
                }
            }
        }
    }


    // ----------  ENVIAR VOTO  ----------

    fun enviarVoto() {

        CoroutineScope(Dispatchers.IO).launch {
            val respuesta = repositorio.enviarVoto(
                VotoEnviar(
                    gatoSeleccionado.value?.image?.id.toString(),
                    "gabatx",
                    1
                )
            )
            withContext(Dispatchers.Main) {
                if (respuesta.isSuccessful && respuesta.code() == 200) {
                    if (respuesta.body()?.message == "SUCCESS") {

                        listaVotosGatos() // Refresca el listado de votos
                        correctoRazas.postValue("Gato votado")
                    }

                } else {
                    errorRazas.postValue(respuesta.errorBody().toString())
                }
            }
        }
    }


    // ----------  BORRAR VOTO  ----------

    fun borrarVoto() {
        // Selecciono el voto que corresponde al gato
        val voto = listaVotosGatos.value?.filter { (it.imageId == gatoSeleccionado.value?.image?.id) }

        CoroutineScope(Dispatchers.Main).launch {
            voto.let {
                // Borro el gato de la lista de votos
                val respuestaBorrarVoto = repositorio.borrarVoto(voto?.get(0)?.id!!)

                if (respuestaBorrarVoto.isSuccessful && respuestaBorrarVoto.code() == 200) {
                    listaVotosGatos() // Refresca el listado de votos
                    correctoRazas.postValue("Voto borrado")
                } else {
                    errorRazas.postValue("Error al borrar el voto")
                }
            }
        }
    }

    // ----------  VALIDAR VOTO ----------

    fun validaVoto() {
        val voto = listaVotosGatos.value?.filter { (it.imageId == gatoSeleccionado.value?.image?.id) }
        gatoVotado.value = voto?.isNotEmpty() // Da el valor true o false dependiendo si está vacío o no
    }


    //  ---------- ---------- ----------
    // ----------  FAVORITOS  ----------
    //  ---------- ---------- ----------


    fun listaFavoritosGatos() {

        CoroutineScope(Dispatchers.IO).launch {
            val respuestaListaFavoritos = repositorio.listaTodosFavoritos("gabatx")

            withContext(Dispatchers.Main) {
                if (respuestaListaFavoritos.isSuccessful && respuestaListaFavoritos.code() == 200) {
                    respuestaListaFavoritos.body().let {
                        listaFavoritosGatos.postValue(it)
                    }
                } else {
                    errorRazas.postValue("Error al mostrar la lista de favoritos")
                }
            }
        }
    }
    // ----------  ENVIAR FAVORITO  ----------

    fun enviarFavorito() {

        CoroutineScope(Dispatchers.IO).launch {
            val respuesta = repositorio.enviarFavorito(
                FavoritoEnviarFavorito(
                    gatoSeleccionado.value?.referenceImageId.toString(),
                    "gabatx"
                )
            )
            withContext(Dispatchers.Main) {
                if (respuesta.isSuccessful && respuesta.code() == 200) {
                    respuesta.body()?.let {
                        listaFavoritosGatos() // Refresca el listado de favoritos
                        correctoRazas.postValue("Favorito enviado")
                    }
                } else {
                    errorRazas.postValue("Error al enviar favorito")
                }
            }
        }
    }

    // ----------  BORRAR FAVORITO  ----------
    fun borrarFavorito() {
        // Selecciono el favorito que corresponde al gato
        val favorito = listaFavoritosGatos.value?.filter { (it.imageId == gatoSeleccionado.value?.image?.id) }

        CoroutineScope(Dispatchers.IO).launch {
            // Borro el gato de la lista de favoritos
            val respuestaBorrarFavorito = repositorio.borrarFavorito(favorito?.get(0)?.id!!)

            withContext(Dispatchers.Main) {

                respuestaBorrarFavorito.let {

                    if (respuestaBorrarFavorito.isSuccessful && respuestaBorrarFavorito.code() == 200) {
                        listaFavoritosGatos() // Refresca el listado de favoritos
                        correctoRazas.postValue("Favorito borrado")
                    } else {
                        errorRazas.postValue("Error al borrar el Favorito")
                    }
                }
            }
        }
    }

    // ----------  VALIDAR FAVORITO ----------
    fun validaFavorito() {
        val voto = listaFavoritosGatos.value?.filter { (it.imageId == gatoSeleccionado.value?.image?.id) }
        gatoFavorito.value = voto?.isNotEmpty() // Da el valor true o false dependiendo si está vacío o no
    }
}
