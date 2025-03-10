package net.leo.numeros_ingles

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout

class MainActivity : AppCompatActivity() {

    private var isEnglish = false
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var gridLayout: GridLayout
    private lateinit var btnSpanish: Button
    private lateinit var btnEnglish: Button

    // Arrays con los números en español
    private val spanishNumberNames = arrayOf(
        "Uno", "Dos", "Tres", "Cuatro", "Cinco",
        "Seis", "Siete", "Ocho", "Nueve", "Diez"
    )

    // Arrays con los números en inglés
    private val englishNumberNames = arrayOf(
        "One", "Two", "Three", "Four", "Five",
        "Six", "Seven", "Eight", "Nine", "Ten"
    )

    // Digitos
    private val digits = arrayOf(
        "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "10"
    )

    // IDs de los recursos de audio en español
    private val spanishSounds = intArrayOf(
        R.raw.es_one, R.raw.es_two, R.raw.es_three, R.raw.es_four, R.raw.es_five,
        R.raw.es_six, R.raw.es_seven, R.raw.es_eight, R.raw.es_nine, R.raw.es_ten
    )

    // IDs de los recursos de audio en inglés
    private val englishSounds = intArrayOf(
        R.raw.en_one, R.raw.en_two, R.raw.en_three, R.raw.en_four, R.raw.en_five,
        R.raw.en_six, R.raw.en_seven, R.raw.en_eight, R.raw.en_nine, R.raw.en_ten
    )

    // Colores para las tarjetas de números
    private val cardColors = intArrayOf(
        R.color.number_1, R.color.number_2, R.color.number_3, R.color.number_4, R.color.number_5,
        R.color.number_6, R.color.number_7, R.color.number_8, R.color.number_9, R.color.number_10
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        gridLayout = findViewById(R.id.gridLayout)
        btnSpanish = findViewById(R.id.btnSpanish)
        btnEnglish = findViewById(R.id.btnEnglish)

        // Configurar listeners para los botones
        btnSpanish.setOnClickListener {
            isEnglish = false
            updateNumberGrid()
        }

        btnEnglish.setOnClickListener {
            isEnglish = true
            updateNumberGrid()
        }

        // Mostrar la cuadrícula inicial en español
        updateNumberGrid()
    }

    private fun updateNumberGrid() {
        // Limpiar la cuadrícula
        gridLayout.removeAllViews()

        // Agregar elementos de números a la cuadrícula
        for (i in 0 until 10) {
            val numberView = layoutInflater.inflate(R.layout.number_item, gridLayout, false)

            val digitTextView = numberView.findViewById<TextView>(R.id.digitTextView)
            val numberNameTextView = numberView.findViewById<TextView>(R.id.numberNameTextView)

            // Establecer el dígito
            digitTextView.text = digits[i]

            // Establecer el nombre del número según el idioma
            numberNameTextView.text = if (isEnglish) englishNumberNames[i] else spanishNumberNames[i]

            // Establecer la etiqueta para identificar el número
            numberView.setOnClickListener {
                playNumberSound(i)
            }

            // Agregar a la cuadrícula
            gridLayout.addView(numberView)
        }
    }

    private fun playNumberSound(numberIndex: Int) {
        // Detener cualquier reproducción previa
        mediaPlayer?.release()

        // Reproducir el sonido según el idioma
        val soundId = if (isEnglish) englishSounds[numberIndex] else spanishSounds[numberIndex]
        mediaPlayer = MediaPlayer.create(this, soundId)
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}