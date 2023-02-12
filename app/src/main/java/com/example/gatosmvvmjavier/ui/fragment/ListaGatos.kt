package com.example.gatosmvvmjavier.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gatosmvvmjavier.R
import com.example.gatosmvvmjavier.databinding.FragmentListaGatosBinding
import com.example.gatosmvvmjavier.domain.models.Gato
import com.example.gatosmvvmjavier.ui.adapter.AdapterGatos
import com.example.gatosmvvmjavier.viewmodel.ViewModelGatos


class ListaGatos : Fragment() {
    private lateinit var binding: FragmentListaGatosBinding
    private lateinit var adapter: AdapterGatos
    private var pullToRefreshWorking = false
    private val viewModelGatos: ViewModelGatos by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        // Esto para decirle a la actividad que el menu lo va a poner el fragment
        setHasOptionsMenu(true)
        binding = FragmentListaGatosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Descargo toda la información en el viewModel
        viewModelGatos.listaTodosGatos()
        viewModelGatos.listaVotosGatos()
        viewModelGatos.listaFavoritosGatos()

        viewModelGatos.listaVotosGatos.value.let {
            configuracionReyclerView()
        }

        viewModelGatos.listaTodosGatos.observe(viewLifecycleOwner) {
            adapter.actualizarLista(it)
        }

        // ----- ----- -----  SWIPE: ----- ----- ----- -----
        // Esto es para que el swipe refrescar funcione
        binding.swipe.setOnRefreshListener {
            viewModelGatos.listaTodosGatos()
        }
        // Muestra y oculta dependiendo del resultado de mostrarSimboloCarga en el viewModel
        viewModelGatos.mostrarSimboloCarga.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = it
        }
        // Apariencia del swipe
        binding.swipe.setColorSchemeColors(Color.MAGENTA, Color.YELLOW)
        binding.swipe.setColorSchemeResources(R.color.azul, R.color.azul_claro)
        binding.swipe.setSize(SwipeRefreshLayout.LARGE)
        //  ------   FIN SWIPE  ------


    } //  ------------------------   Fin onViewCreated  ------------------------



    private fun configuracionReyclerView() {
        binding.recylclerViewTodosGatos.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        // El adapter carga el recyclerView
        adapter = AdapterGatos(viewModelGatos)
        viewModelGatos.listaTodosGatos.value?.let { adapter.actualizarLista(it) }
        binding.recylclerViewTodosGatos.adapter = adapter
    }

    // Infla en menú superior que hemos creado en res/menu/menu_superior
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_superior, menu)

        // El inflado del menu (res/menu/menu_superior)
        val item = menu.findItem(R.id.app_bar_search_buscar)
        val searchView = item?.actionView as SearchView

        // El listener del buscador
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }

    // La función sirve de evento al pulsar un item del toolbar superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Recoge el valor del listado de gatos actual
        val listadoGatos = viewModelGatos.listaTodosGatos.value

        if (item.itemId == R.id.boton_ordenar_por_nombre) {
            // Ordenar por nombre
            listadoGatos?.sortBy { it.name }
            adapter.actualizarLista(listadoGatos!!)
        }
        if (item.itemId == R.id.boton_ordenar_por_pais) {
            // Ordenar por pais
            listadoGatos?.sortBy { it.origin }
            adapter.actualizarLista(listadoGatos!!)
        }
        if (item.itemId == R.id.boton_ordenar_por_exp_vida) {
            // Ordenar por exp vida
            listadoGatos?.sortBy { it.lifeSpan }
            adapter.actualizarLista(listadoGatos!!)
        }

        return super.onOptionsItemSelected(item)
    }
}


