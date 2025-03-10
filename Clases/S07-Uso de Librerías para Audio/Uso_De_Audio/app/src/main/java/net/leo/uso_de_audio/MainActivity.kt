package net.leo.uso_de_audio

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnReproducir: Button = findViewById(R.id.btnReproducir)
        val btnRemoto: Button = findViewById(R.id.btnRemoto)
        val btnDetener: Button = findViewById(R.id.btnDetener)

        btnReproducir.setOnClickListener { reproducirAudioLocal() }
        btnRemoto.setOnClickListener { reproducirAudioRemoto() }
        btnDetener.setOnClickListener { detenerAudio() }
    }

    private fun reproducirAudioLocal() {
        detenerAudio()
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_test)
        mediaPlayer?.start()
    }

    private fun reproducirAudioRemoto() {
        detenerAudio()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource("https://tonosmovil.net/wp-content/uploads/tonosmovil.net_himno_champions_league.mp3")
        mediaPlayer?.setOnPreparedListener { it.start() }
        mediaPlayer?.prepareAsync()
    }

    private fun detenerAudio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
            }
        }
        mediaPlayer = null
    }
}
