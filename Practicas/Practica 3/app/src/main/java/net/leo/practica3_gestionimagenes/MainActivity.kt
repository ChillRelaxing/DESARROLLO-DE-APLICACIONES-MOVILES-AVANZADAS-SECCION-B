package net.leo.practica3_gestionimagenes

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.Transformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    // Declaración de variables
    private lateinit var imageView: ImageView
    private lateinit var btnRotate: Button
    private lateinit var btnZoom: Button
    private lateinit var btnMirror: Button
    private lateinit var btnGrayscale: Button
    private lateinit var btnCrop: Button
    private lateinit var btnGallery: Button
    private lateinit var btnSaveImage: Button

    private var currentImageUri: Uri? = null
    private var currentBitmap: Bitmap? = null
    private val imageUrl = "https://imgs.search.brave.com/qvHe7s8P63qpcOCIGkZrMrF4OUbdX-ws6uPDiyuk5tc/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly91cGxv/YWQud2lraW1lZGlh/Lm9yZy93aWtpcGVk/aWEvY29tbW9ucy9i/L2I5L0Nhc3Bhcl9E/YXZpZF9GcmllZHJp/Y2hfLV9XYW5kZXJl/cl9hYm92ZV90aGVf/c2VhX29mX2ZvZy5q/cGc"

    // Solicitar permisos para abrir galería
    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            openGallery()
        } else {
            Toast.makeText(this, "Se requieren permisos para acceder a la galería", Toast.LENGTH_SHORT).show()
        }
    }

    // Lanzador para seleccionar imagen de la galería
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            currentImageUri = it
            imageView.load(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar componentes
        setInitComponents()
        // Cargar imagen previa
        loadPreviewImage()
        // Configurar eventos de botones
        setActionEvents()
    }

    private fun setInitComponents() {
        imageView = findViewById(R.id.imageView)
        btnRotate = findViewById(R.id.btnRotate)
        btnZoom = findViewById(R.id.btnZoom)
        btnMirror = findViewById(R.id.btnMirror)
        btnGrayscale = findViewById(R.id.btnGrayscale)
        btnCrop = findViewById(R.id.btnCrop)
        btnGallery = findViewById(R.id.btnGallery)
        btnSaveImage = findViewById(R.id.btnSaveImage)
    }

    private fun loadPreviewImage() {
        imageView.load(imageUrl) {
            listener(
                onSuccess = { _, result ->
                    currentBitmap = (result.drawable as BitmapDrawable).bitmap
                }
            )
        }
    }

    private fun setActionEvents() {
        btnRotate.setOnClickListener {
            applyTransformation(RotationTransformation(90f))
        }

        btnZoom.setOnClickListener {
            applyTransformation(ScaleTransformation(1.5f, 1.5f))
        }

        btnMirror.setOnClickListener {
            applyTransformation(MirrorTransformation(true))
        }

        btnGrayscale.setOnClickListener {
            applyTransformation(GrayscaleTransformation())
        }

        btnCrop.setOnClickListener {
            currentBitmap?.let { bitmap ->
                val centerX = bitmap.width / 4
                val centerY = bitmap.height / 4
                val size = minOf(bitmap.width / 2, bitmap.height / 2)
                applyTransformation(CropTransformation(centerX, centerY, size, size))
            }
        }

        btnGallery.setOnClickListener {
            checkPermissionsAndOpenGallery()
        }

        btnSaveImage.setOnClickListener {
            saveImageToGallery()
        }
    }

    private fun checkPermissionsAndOpenGallery() {
        val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isEmpty()) {
            openGallery()
        } else {
            permissionsLauncher.launch(permissionsToRequest)
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun applyTransformation(transformation: Transformation) {
        CoroutineScope(Dispatchers.Main).launch {
            val source = if (currentImageUri != null) {
                currentImageUri
            } else {
                imageUrl
            }

            val request = ImageRequest.Builder(this@MainActivity)
                .data(source)
                .transformations(transformation)
                .target { drawable ->
                    imageView.setImageDrawable(drawable)
                    currentBitmap = (drawable as BitmapDrawable).bitmap
                }
                .build()

            val imageLoader = ImageLoader.Builder(this@MainActivity)
                .build()

            imageLoader.enqueue(request)
        }
    }

    private fun saveImageToGallery() {
        val bitmap = currentBitmap ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageFileName = "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    val contentResolver = applicationContext.contentResolver
                    val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    imageUri?.let {
                        contentResolver.openOutputStream(it)?.use { outputStream ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                        } ?: throw Exception("No se pudo abrir el archivo para escritura")
                    }
                } else {
                    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val image = File(imagesDir, imageFileName)
                    FileOutputStream(image).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    }
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Imagen guardada correctamente", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}