package com.example.gatosmvvmjavier.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.gatosmvvmjavier.databinding.FragmentDetalleStatsBinding
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos


class DetalleStatsFragment : Fragment() {

    private lateinit var binding : FragmentDetalleStatsBinding
    private val viewModelGatos: ViewModelGatos by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetalleStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gato = viewModelGatos.gatoSeleccionado.value

        binding.adaptability.text = gato?.adaptability.toString()
        binding.affectionLevel.text = gato?.affectionLevel.toString()
        binding.childFriendly.text = gato?.childFriendly.toString()
        binding.dogFriendly.text = gato?.dogFriendly.toString()
        binding.energyLevel.text = gato?.energyLevel.toString()
        binding.healthIssues.text = gato?.healthIssues.toString()
        binding.sheddingLevel.text = gato?.sheddingLevel.toString()
        binding.intelligence.text = gato?.intelligence.toString()
        binding.socialNeeds.text = gato?.socialNeeds.toString()


    }

}