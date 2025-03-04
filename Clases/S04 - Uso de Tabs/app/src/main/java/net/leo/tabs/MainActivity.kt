package net.leo.tabs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar Toolbar como ActionBar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)  // <-- IMPORTANTE

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.quienSoyFragment, R.id.queEstudioFragment, R.id.tecnologiasFragment)
        )

        // Ahora esta línea funcionará sin error
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavView.setupWithNavController(navController)
    }
}
