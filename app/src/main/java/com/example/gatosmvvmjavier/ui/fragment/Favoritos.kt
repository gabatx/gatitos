package com.example.gatosmvvmjavier.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gatosmvvmjavier.databinding.FragmentFavoritosBinding
import com.example.gatosmvvmjavier.ui.adapter.AdapterFavoritos
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos

class Favoritos : Fragment() {
    lateinit var binding: FragmentFavoritosBinding
    private lateinit var adapter: AdapterFavoritos
    private val viewModelGatos: ViewModelGatos by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuracionReyclerView(viewModelGatos)
    }

    private fun configuracionReyclerView(viewModelGatos: ViewModelGatos) {
        binding.recyclerViewFavoritos.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        // El adapter carga el recyclerView
        adapter = AdapterFavoritos(viewModelGatos)
        viewModelGatos.listaFavoritosGatos.value.let { adapter.actualizarListaFavoritos(it) }
        binding.recyclerViewFavoritos.adapter = adapter
    }
}