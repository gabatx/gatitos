package com.example.gatosmvvmjavier.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gatosmvvmjavier.databinding.FragmentVotosBinding
import com.example.gatosmvvmjavier.ui.adapter.AdapterVotos
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos

class Votos : Fragment() {
    lateinit var binding: FragmentVotosBinding
    private lateinit var adapter: AdapterVotos
    private val viewModelGatos: ViewModelGatos by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentVotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuracionReyclerView(viewModelGatos)
    }

    // --------- FUNCIONES ------------

    private fun configuracionReyclerView(viewModelGatos: ViewModelGatos) {
        binding.recyclerViewVotos.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        // El adapter carga el recyclerView
        adapter = AdapterVotos(viewModelGatos)
        viewModelGatos.listaVotosGatos.value.let {
            adapter.actualizarListaVotos(it!!)
        }
        binding.recyclerViewVotos.adapter = adapter
    }
}