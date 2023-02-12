package com.example.gatosmvvmjavier.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.gatosmvvmjavier.databinding.FragmentDetalleInformacionBinding
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos


class DetalleInformacionFragment : Fragment() {

    private lateinit var binding: FragmentDetalleInformacionBinding
    private val viewModelGatos: ViewModelGatos by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetalleInformacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gato = viewModelGatos.gatoSeleccionado.value

        binding.weight.text = "${gato?.weight?.imperial} kg"
        binding.temperament.text = gato?.temperament.toString()
        binding.origin.text = gato?.origin.toString()
        binding.lifeSpan.text = "${ gato?.lifeSpan } years"
        binding.description.text = gato?.description.toString()
    }
}