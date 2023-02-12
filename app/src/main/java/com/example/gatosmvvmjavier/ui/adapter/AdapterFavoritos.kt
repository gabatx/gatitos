package com.example.gatosmvvmjavier.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatosmvvmjavier.R
import com.example.gatosmvvmjavier.databinding.HolderGatoFavoritoBinding
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.domain.models.favorito.FavoritoRespuestaTodosFavoritos
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos


class AdapterFavoritos(val viewModelGatos: ViewModelGatos) : RecyclerView.Adapter<AdapterFavoritos.ViewHolderFavorito>() {


    private var listaFavoritos = ArrayList<FavoritoRespuestaTodosFavoritos>()

    inner class ViewHolderFavorito(val binding: HolderGatoFavoritoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFavorito {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderGatoFavoritoBinding.inflate(layoutInflater, parent, false)
        return ViewHolderFavorito(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderFavorito, position: Int) {
        val favorito: FavoritoRespuestaTodosFavoritos = listaFavoritos[position]

        // Imagen gato
        if (favorito.image.url != null) {
            Glide.with(holder.binding.root.context)
                .load(favorito.image.url)
                .into(holder.binding.imageViewFavorito)
        } else {
            Glide.with(holder.binding.root.context)
                .load(R.drawable.gatito)
                .into(holder.binding.imageViewFavorito)
        }

        holder.binding.botonXEliminarFavorito.setOnClickListener {
            // Saco el gato al que se le por la id que se quiere eliminar de favoritos para borrarlo en el viewModel
            val gato = viewModelGatos.listaTodosGatos.value?.filter {it.image?.id  == favorito.imageId}
            viewModelGatos.gatoSeleccionado.value = gato?.get(0)
            viewModelGatos.borrarFavorito() // Borro el gato de la Api
            eliminarFavorito(position) // Borro el gato del adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun eliminarFavorito(position: Int) {
        if (position < listaFavoritos.size) {
            listaFavoritos.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listaFavoritos.size
    }

    fun actualizarListaFavoritos(listaFavoritosActualizada: ArrayList<FavoritoRespuestaTodosFavoritos>?) {
        listaFavoritos.clear()
        listaFavoritos.addAll(listaFavoritosActualizada!!)
        notifyDataSetChanged()
    }
}