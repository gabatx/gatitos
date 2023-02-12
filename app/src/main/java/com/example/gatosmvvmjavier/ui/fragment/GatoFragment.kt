package com.example.gatosmvvmjavier.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.gatosmvvmjavier.R
import com.example.gatosmvvmjavier.databinding.FragmentGatoBinding
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class GatoFragment : Fragment() {

    private lateinit var binding: FragmentGatoBinding
    private val viewModelGatos: ViewModelGatos by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGatoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Rellena los datos de cabecera del detalle
        rellenaGatoNoTabs()

        // Si los mensaje de error o correcto en un toast
        viewModelGatos.errorRazas.observe(viewLifecycleOwner) {Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()}
        viewModelGatos.correctoRazas.observe(viewLifecycleOwner) {Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()}

        // ------------ COMPRUEBA EL ESTADO DEL VOTO O DEL FAVORITO PARA PINTARLO ------------
        viewModelGatos.validaVoto()
        viewModelGatos.validaFavorito()

        // ---- PINTA EL VOTO (COMO MARCADO O NO) ----
        viewModelGatos.gatoVotado.observe(viewLifecycleOwner){
            if (it) {
                cambiaFondoIcono(R.color.azul, binding.floatingActionButtonVoto)
            } else {
                cambiaFondoIcono(
                    R.color.me_gusta_desactivado,
                    binding.floatingActionButtonVoto
                )
            }
        }

        // ---------------- PINTA EL FAVORITO -----------------
        viewModelGatos.gatoFavorito.observe(viewLifecycleOwner){ activado ->
            if (activado) {
                cambiaFondoIcono(R.color.favorito, binding.floatingActionButtonFavorito)
                binding.floatingActionButtonFavorito.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                cambiaFondoIcono(R.color.me_gusta_desactivado, binding.floatingActionButtonFavorito)
                binding.floatingActionButtonFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        // BOTÓN ENVIAR FAVORITO
        binding.floatingActionButtonFavorito.setOnClickListener {

            // Comprueba si gatoFavorito es true o false (ha sido pulsado o no)
            if (!viewModelGatos.gatoFavorito.value!!) {
                // Cambia el icono al pulsar sobre el botón
                binding.floatingActionButtonFavorito.setImageResource(R.drawable.ic_baseline_favorite_24)
                cambiaFondoIcono(R.color.favorito, binding.floatingActionButtonFavorito)
                //emite favorito(imagen del gato)
                viewModelGatos.enviarFavorito()
                viewModelGatos.gatoFavorito.value = !viewModelGatos.gatoFavorito.value!!
            } else {
                binding.floatingActionButtonFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                cambiaFondoIcono(R.color.me_gusta_desactivado, binding.floatingActionButtonFavorito)
                viewModelGatos.borrarFavorito()
                viewModelGatos.gatoFavorito.value = !viewModelGatos.gatoFavorito.value!!
            }
        }

        // BOTÓN DE VOTAR
        binding.floatingActionButtonVoto.setOnClickListener {
            // Comprueba si el tiene un color el icono o tiene otro
            if (!viewModelGatos.gatoVotado.value!!) {
                cambiaFondoIcono(R.color.azul, binding.floatingActionButtonVoto)
                viewModelGatos.enviarVoto()
                viewModelGatos.gatoVotado.value = !viewModelGatos.gatoVotado.value!! // Cambia el voto al contrario
            } else {
                cambiaFondoIcono(R.color.me_gusta_desactivado, binding.floatingActionButtonVoto)
                viewModelGatos.borrarVoto()
                viewModelGatos.gatoVotado.value = !viewModelGatos.gatoVotado.value!!
            }
        }

        // CONFIGURACIÓN PESTAÑAS
        // Al pulsar recoge el tab donde se ha pulsado y lo envía a la función que navega entre los fragmentos tabs
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val id = tab!!.position
                navigateToFragment(id)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
        navigateToFragment(0)
    }


    // NAVEGA A LA PESTAÑA CORRESPONDIENTE
    private fun navigateToFragment(itemId: Int) {
        //fragment y titulo por defecto
        val fragment: Fragment = when (itemId) {
            1    -> DetalleStatsFragment()
            else -> DetalleInformacionFragment()
        }

        cargaFragmentTab(fragment)
    }

    // CARGA EL FRAGMENT EN LA PESTAÑA
    private fun cargaFragmentTab(fragment: Fragment) {

        val fragmentManager = activity?.supportFragmentManager

        // transacción del fragment al framelayout
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frameLayout_contenedor, fragment)
        transaction?.commit()
    }



    // CAMBIA EL ESTADO DEL BOTÓN DE VOTO
    private fun cambiaFondoIcono(color: Int, boton: FloatingActionButton) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boton.backgroundTintList = ColorStateList.valueOf(
                resources.getColorStateList(
                    color,
                    null
                ).defaultColor
            )
        }
    }

    // RELLENA LOS DATOS DEL GATO
    private fun rellenaGatoNoTabs() {
        val gatoFragment = viewModelGatos.gatoSeleccionado.value
        // Introducir url gato en imagen
        binding.toolbar.title = gatoFragment?.name
        Glide
            .with(this)
            .load(gatoFragment?.image?.url)
            .into(binding.imagenGato)

        binding.floatingActionButtonWikipedia.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(gatoFragment?.wikipediaUrl)
            startActivity(intent)
        }

        cargaFragmentTab(DetalleInformacionFragment())
    }
}