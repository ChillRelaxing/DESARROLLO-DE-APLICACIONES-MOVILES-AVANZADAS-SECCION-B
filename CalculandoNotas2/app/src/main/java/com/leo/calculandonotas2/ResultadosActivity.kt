package com.leo.calculandonotas2

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        val nombre = intent.getStringExtra("nombre") ?: "Desconocido"
        val resultado = intent.getStringExtra("resultado") ?: "No Aprobado"
        val promedio = intent.getDoubleExtra("promedio", 0.0)
        val notaMinima = intent.getDoubleExtra("notaMinima", 0.0)

        val resultadoFinal = findViewById<TextView>(R.id.resultadoFinal)
        val salirButton = findViewById<Button>(R.id.salirButton)

        val mensaje = if (resultado == "Aprobado") {
            resultadoFinal.setTextColor(Color.GREEN)
            "¡Aprobado! Promedio: %.2f".format(promedio)
        } else {
            resultadoFinal.setTextColor(Color.RED)
            "No Aprobado. Faltan %.2f puntos para aprobar.".format(notaMinima - promedio)
        }
        resultadoFinal.text = "$nombre\n$mensaje"

        salirButton.setOnClickListener {
            finish()  // Regresa al inicio
        }
    }
}



