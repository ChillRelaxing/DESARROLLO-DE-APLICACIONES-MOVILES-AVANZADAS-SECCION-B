package com.leo.calculandonotas2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NotasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etNotaMinima = findViewById<EditText>(R.id.etNotaMinima)
        val nota1 = findViewById<EditText>(R.id.nota1)
        val nota2 = findViewById<EditText>(R.id.nota2)
        val nota3 = findViewById<EditText>(R.id.nota3)
        val calcularButton = findViewById<Button>(R.id.calcularButton)

        calcularButton.setOnClickListener {
            val nombre = etNombre.text.toString()
            val notaMinima = etNotaMinima.text.toString().toDoubleOrNull() ?: 0.0
            val n1 = nota1.text.toString().toDoubleOrNull() ?: 0.0
            val n2 = nota2.text.toString().toDoubleOrNull() ?: 0.0
            val n3 = nota3.text.toString().toDoubleOrNull() ?: 0.0

            val promedio = (n1 + n2 + n3) / 3
            val resultado = if (promedio >= notaMinima) "Aprobado" else "No Aprobado"

            val intent = Intent(this, ResultadosActivity::class.java).apply {
                putExtra("nombre", nombre)
                putExtra("resultado", resultado)
                putExtra("promedio", promedio)
                putExtra("notaMinima", notaMinima)
            }
            startActivity(intent)
        }
    }
}


