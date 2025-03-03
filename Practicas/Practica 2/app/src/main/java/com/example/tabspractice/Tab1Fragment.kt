package com.example.tabspractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentContainer


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Tab1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text = "Contenido de la pestaña 1"
        return view
    }
}