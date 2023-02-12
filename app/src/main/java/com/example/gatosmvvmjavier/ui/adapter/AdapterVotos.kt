package com.example.gatosmvvmjavier.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatosmvvmjavier.databinding.HolderGatoVotadoBinding
import com.example.gatosmvvmjavier.domain.models.voto.VotoTodosRespuesta
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AdapterVotos (val viewModelGatos: ViewModelGatos) : RecyclerView.Adapter<AdapterVotos.ViewHolderVoto>() {

    private val listaVotos = ArrayList<VotoTodosRespuesta>()

    inner class ViewHolderVoto(val binding: HolderGatoVotadoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVoto {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderGatoVotadoBinding.inflate(layoutInflater, parent, false)
        return ViewHolderVoto(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolderVoto, position: Int) {
        val voto = listaVotos[position]

        holder.binding.fechaVoto.text = formateaFecha(voto.createdAt)

        val imagen = "https://cdn2.thecatapi.com/images/${voto.imageId}.jpg"
        // Imagen gato
        Glide.with(holder.binding.root.context)
            .load(imagen)
            .into(holder.binding.imageViewVoto)

        holder.binding.eliminarVoto.setOnClickListener {
            val gato = viewModelGatos.listaTodosGatos.value?.filter {it.image?.id  == voto.imageId}
            viewModelGatos.gatoSeleccionado.value = gato?.get(0)
            viewModelGatos.borrarVoto() // Borro el voto de la Api
            eliminarGatoVotadoInterfaz(position)
        }
    }

    override fun getItemCount(): Int {
        return listaVotos.size
    }

    fun actualizarListaVotos(listaVotosActualizada: ArrayList<VotoTodosRespuesta>) {
        listaVotos.clear()
        listaVotos.addAll(listaVotosActualizada)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun eliminarGatoVotadoInterfaz(position: Int) {
        if (position < listaVotos.size) {
            listaVotos.removeAt(position)
            notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formateaFecha(fecha: String): String {
        val zonaHoraria = ZonedDateTime.parse(fecha) // Clase que almacena todos los campos de fecha y hora,
        return zonaHoraria.format(DateTimeFormatter.ofPattern("dd-MMMM-uuuu HH:mm")) // Retornamos con el formato deseado (dd.MMMM.uuuu HH:mm)
    }
}

