package com.leo.juegocirculos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita la funcionalidad edge-to-edge
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        // Aplica los insets del sistema (para que el contenido no se superponga a las barras de sistema)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura el listener del botón para iniciar JuegoActivity
        findViewById<Button>(R.id.ButtonIniciar).setOnClickListener {
            startActivity(Intent(this, JuegoActivity::class.java))
        }
    }
}
