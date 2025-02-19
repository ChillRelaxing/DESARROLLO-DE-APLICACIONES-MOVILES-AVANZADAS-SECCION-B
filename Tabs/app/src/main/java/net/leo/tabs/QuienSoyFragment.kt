package net.leo.tabs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuienSoyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_quien_soy,
            container, false)
        view.findViewById<TextView>(R.id.tvNombre).text = "Nombre: Leo Steven"
        view.findViewById<TextView>(R.id.tvApellido).text = "Apellido: Hernandez Salmeron"
        view.findViewById<TextView>(R.id.tvCarnet).text = "Carnet: U20210358"
        view.findViewById<TextView>(R.id.tvTelefono).text = "Teléfono: 7650 9466"
        view.findViewById<Button>(R.id.btnEscribeme).setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://wa.me/76509466")
            startActivity(intent)
        }
        return view
    }
}