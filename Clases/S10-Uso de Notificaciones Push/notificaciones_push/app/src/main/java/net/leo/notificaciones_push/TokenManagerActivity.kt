package net.leo.notificaciones_push

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class TokenManagerActivity : AppCompatActivity() {

    private lateinit var etToken: EditText
    private lateinit var btnGetToken: Button
    private lateinit var btnCopyToken: Button
    private var fcmToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_manager)

        // Initialize views
        etToken = findViewById(R.id.etToken)
        btnGetToken = findViewById(R.id.btnGetToken)
        btnCopyToken = findViewById(R.id.btnCopyToken)

        // Set click listeners
        btnGetToken.setOnClickListener {
            getAndDisplayToken()
        }

        btnCopyToken.setOnClickListener {
            copyTokenToClipboard()
        }
    }

    private fun getAndDisplayToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "Error al obtener el token", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }

            fcmToken = task.result
            etToken.setText(fcmToken)
            Toast.makeText(this, "Token obtenido con éxito", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyTokenToClipboard() {
        if (fcmToken.isNotEmpty()) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("FCM Token", fcmToken)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Token copiado al portapapeles", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Primero debe obtener un token", Toast.LENGTH_SHORT).show()
        }
    }
}