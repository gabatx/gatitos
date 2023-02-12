package com.example.gatosmvvmjavier.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gatosmvvmjavier.R
import com.example.gatosmvvmjavier.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: androidx.navigation.NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --------------------------------------------------------------------------------------------------
        setSupportActionBar(binding.toolbar)
        // --------------------------------------------------------------------------------------------------
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_fragment_lista_todos,
                R.id.nav_fragment_votos,
                R.id.nav_fragment_favoritos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // Se llama a este método cada vez que el usuario elige navegar hacia arriba dentro de la jerarquía de actividad de su aplicación desde la barra de acción.
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}