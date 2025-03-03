package net.leo.practica3_gestionimagenes

import android.graphics.Bitmap
import android.graphics.Matrix
import coil.size.Size
import coil.transform.Transformation

class RotationTransformation(private val degrees: Float) : Transformation {
    // Solo utiliza la propiedad cacheKey, no el método
    override val cacheKey: String = "rotation=$degrees"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val matrix = Matrix().apply {
            postRotate(degrees)
        }
        return Bitmap.createBitmap(input, 0, 0, input.width, input.height, matrix, true)
    }
}
