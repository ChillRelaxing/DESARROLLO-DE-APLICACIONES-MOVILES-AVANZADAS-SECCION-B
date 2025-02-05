package com.leo.juegocirculos
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class JuegoActivity : AppCompatActivity() {
    private lateinit var cuadrilla: GridLayout
    private lateinit var textoNivel: TextView
    private lateinit var circulos: List<View>
    private val manejador = Handler(Looper.getMainLooper())
    private var puntaje = 0
    private var nivelActual = 1
    private var circulosPorNivel = 5
    private var circulosVerdes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        cuadrilla = findViewById(R.id.gridLayout)
        textoNivel = findViewById(R.id.levelText)
        circulos = List(12) { crearCirculo() }
        circulos.forEach { cuadrilla.addView(it) }
        iniciarNivel()
    }

    private fun crearCirculo(): View {
        return View(this).apply {
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            background = ContextCompat.getDrawable(this@JuegoActivity,
                R.drawable.circle_red)
            setOnClickListener { alHacerClickEnCirculo(this) }
        }
    }

    private fun iniciarNivel() {
        textoNivel.text = "Nivel $nivelActual"
        puntaje = 0
        circulosVerdes = 0
        circulosPorNivel = 5 + (nivelActual * 2)  // Más círculos por nivel
        cuadrilla.columnCount = 3 + (nivelActual / 2)  // Más columnas cada 2 niveles
        reiniciarCirculos()
        mostrarProximoCirculoVerde()
    }

    private fun reiniciarCirculos() {
        circulos.forEach { it.background =
            ContextCompat.getDrawable(this, R.drawable.circle_red) }
    }

    private fun mostrarProximoCirculoVerde() {
        if (circulosVerdes < circulosPorNivel) {
            // Reducir el tiempo entre círculos según el nivel
            val delay = 1000L - (nivelActual * 200L)
            manejador.postDelayed({
                cambiarCirculoAleatorio()
                circulosVerdes++
                mostrarProximoCirculoVerde()
            }, delay.coerceAtLeast(300)) // Mínimo 300ms
        } else {
            if (nivelActual >= 3) { // Máximo 5 niveles
                finalizarJuego(true)
            } else {
                nivelActual++
                iniciarNivel()
            }
        }
    }

    private fun cambiarCirculoAleatorio() {
        val circulosRojos = circulos.filter {
            it.background.constantState == ContextCompat.getDrawable(this,
                R.drawable.circle_red)?.constantState }
        if (circulosRojos.isNotEmpty()) {
            val circuloAleatorio = circulosRojos.random()
            circuloAleatorio.background =
                ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }


    private fun alHacerClickEnCirculo(vista: View) {
        if (vista.background.constantState ==
            ContextCompat.getDrawable(this, R.drawable.circle_green)?.constantState) {
            // Animación de acierto
            vista.animate().scaleX(0.8f).scaleY(0.8f).setDuration(200).withEndAction {
                vista.animate().scaleX(1f).scaleY(1f)
            }
            puntaje++
            vista.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
        } else {
            // Animación de error
            vista.animate().rotation(360f).setDuration(500)
            finalizarJuego(false)
        }
    }


    private fun finalizarJuego(completado: Boolean) {
        manejador.removeCallbacksAndMessages(null)
        val intent = Intent(this, ResultadosActivity::class.java)
        intent.putExtra("PUNTAJE", puntaje)
        intent.putExtra("COMPLETADO", completado)
        intent.putExtra("NIVEL", nivelActual)
        startActivity(intent)
        finish()
    }
}
