package net.leo.sensorproximidad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnProximidad: Button = findViewById(R.id.btnProximidad)
        val btnLuminosidad: Button = findViewById(R.id.btnLuminosidad)

        btnProximidad.setOnClickListener {
            val intent = Intent(this, ProximidadActivity::class.java)
            startActivity(intent)
        }

        btnLuminosidad.setOnClickListener {
            val intent = Intent(this, LuminosidadActivity::class.java)
            startActivity(intent)
        }
    }
}