package net.leo.libreraparaimgenes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType

class MainActivity : AppCompatActivity() {
    private lateinit var txtTitulo: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtAutores: TextView
    private lateinit var txtDescripcion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los TextViews
        txtTitulo = findViewById(R.id.txtTitulo)
        txtFecha = findViewById(R.id.txtFecha)
        txtAutores = findViewById(R.id.txtAutores)
        txtDescripcion = findViewById(R.id.txtDescripcion)

        // Crear lista de películas
        val movies = listOf(
            Movie(R.drawable.img1, "Película 1", "2022", "Actor 1, Actor 2", "Descripción de la película 1."),
            Movie(R.drawable.img2, "Película 2", "2021", "Actor 3, Actor 4", "Descripción de la película 2."),
            Movie(R.drawable.img3, "Película 2", "2021", "Actor 3, Actor 4", "Descripción de la película 2."),
            Movie(R.drawable.img4, "Película 2", "2021", "Actor 3, Actor 4", "Descripción de la película 2."),
            Movie(R.drawable.img5, "Película 2", "2021", "Actor 3, Actor 4", "Descripción de la película 2."),
            Movie(R.drawable.img6, "Película 2", "2021", "Actor 3, Actor 4", "Descripción de la película 2.")
        )

        // Configuración del slider
        val adapter = SliderAdapter(movies)
        val imageSlider = findViewById<SliderView>(R.id.imageSlider)

        imageSlider.setSliderAdapter(adapter)

        // Configura animaciones opcionales
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) // Clase corregida
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.startAutoCycle()

        // Añade el listener correcto
        imageSlider.setOnIndicatorClickListener   { position ->
            val movie = movies[position]
            txtTitulo.text = movie.title
            txtFecha.text = "Fecha de lanzamiento: ${movie.releaseDate}"
            txtAutores.text = "Actores principales: ${movie.mainActors}"
            txtDescripcion.text = "Descripción: ${movie.description}"
        }

        val btnVerMas = findViewById<Button>(R.id.btnVerMas)
        btnVerMas.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com"))
            startActivity(intent)
        }
    }
}