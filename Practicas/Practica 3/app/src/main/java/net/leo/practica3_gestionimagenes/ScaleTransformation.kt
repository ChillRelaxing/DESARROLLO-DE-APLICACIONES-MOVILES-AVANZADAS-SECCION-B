package net.leo.practica3_gestionimagenes

import android.graphics.Bitmap
import android.graphics.Matrix
import coil.size.Size
import coil.transform.Transformation

class ScaleTransformation(
    private val scaleX: Float,
    private val scaleY: Float
) : Transformation {
    // Solo utiliza la propiedad cacheKey, no el método
    override val cacheKey: String = "scale=$scaleX,$scaleY"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val matrix = Matrix().apply {
            postScale(scaleX, scaleY)
        }
        return Bitmap.createBitmap(input, 0, 0, input.width, input.height, matrix, true)
    }
}