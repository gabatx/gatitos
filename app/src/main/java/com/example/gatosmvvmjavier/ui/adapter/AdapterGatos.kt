package com.example.gatosmvvmjavier.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatosmvvmjavier.R
import com.example.gatosmvvmjavier.databinding.HolderListaTodosGatosBinding
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos


class AdapterGatos (val viewModelGatos: ViewModelGatos) : RecyclerView.Adapter<AdapterGatos.HolderGato>(),
    Filterable {

    private var listaGatos = ArrayList<Gato>()

    inner class HolderGato(val binding: HolderListaTodosGatosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderGato {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderListaTodosGatosBinding.inflate(layoutInflater, parent, false)
        return HolderGato(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderGato, position: Int) {
        val gato = listaGatos[position]
        holder.binding.nombreGato.text = gato.name
        holder.binding.nombrePais.text = gato.origin
        holder.binding.esperanzaVida.text = "${gato.lifeSpan} años"
        // Imagen gato
        if (gato.image?.url != null) {
            Glide.with(holder.binding.root.context)
                .load(gato.image.url)
                .into(holder.binding.imagenGato)

        } else {
            Glide.with(holder.binding.root.context)
                .load(R.drawable.gatito)
                .into(holder.binding.imagenGato)
        }

        // Bandera:
        gato.countryCode?.let {
            val imagenPais = "https://countryflagsapi.com/png/${gato.countryCode}"
            Glide
                .with(holder.itemView)
                .load(imagenPais)
                .into(holder.binding.imagenPais)
        }


        holder.itemView.setOnClickListener {
            viewModelGatos.gatoSeleccionado.value = gato
            it.findNavController().navigate(R.id.action_nav_fragment_lista_todos_to_frament_gato)
        }
    }

    override fun getItemCount(): Int {
        return listaGatos.size
    }

    fun actualizarLista(listaGatosActualizada: ArrayList<Gato>) {
        listaGatos.clear()
        listaGatos.addAll(listaGatosActualizada)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {

        listaGatos = viewModelGatos.listaTodosGatos.value!!

        return object : Filter() {
            // Aquí va filtrando
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val palabraABuscar = p0.toString()

                listaGatos = if (palabraABuscar.isEmpty()) {
                    listaGatos
                } else {
                    listaGatos.filter {
                        (it.name?.lowercase()?.contains(palabraABuscar.lowercase())!!)
                    } as ArrayList<Gato>
                }
                // Aquí va filtrando
                val filterResults = FilterResults()
                filterResults.values = listaGatos
                // Lo que devuelva aquí lo va a recibir publishResults (más abajo)
                return filterResults
            }

            // Aquí va el listado nuevo
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listaGatos = p1?.values as ArrayList<Gato>
                notifyDataSetChanged()
            }

        }
    }
}