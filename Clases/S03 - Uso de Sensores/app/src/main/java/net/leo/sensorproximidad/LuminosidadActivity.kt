package net.leo.sensorproximidad

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LuminosidadActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var txtLuminosidad: TextView
    private lateinit var txtValorLuminosidad: TextView
    private lateinit var btnEnviarResultado: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luminosidad)

        txtLuminosidad = findViewById(R.id.txtLuminosidad)
        txtValorLuminosidad = findViewById(R.id.txtValorLuminosidad)
        btnEnviarResultado = findViewById(R.id.btnEnviarResultado)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null) {
            txtLuminosidad.text = "Sensor de Luminosidad no disponible"
        }

        btnEnviarResultado.setOnClickListener {
            val valorLuminosidad = txtValorLuminosidad.text.toString()
            val mensaje = "El valor de luminosidad es: $valorLuminosidad"

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, mensaje)
            startActivity(Intent.createChooser(intent, "Enviar mensaje a través de:"))

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_LIGHT) {
                val valorLuminosidad = it.values[0]
                txtValorLuminosidad.text = "$valorLuminosidad LUX"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
