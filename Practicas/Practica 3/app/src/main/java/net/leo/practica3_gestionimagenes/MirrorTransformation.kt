package net.leo.practica3_gestionimagenes

import android.graphics.Bitmap
import android.graphics.Matrix
import coil.size.Size
import coil.transform.Transformation

class MirrorTransformation(private val horizontal: Boolean = true) : Transformation {
    // Solo utiliza la propiedad cacheKey, no el método
    override val cacheKey: String = "mirror=$horizontal"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val matrix = Matrix().apply {
            if (horizontal) {
                postScale(-1f, 1f)
            } else {
                postScale(1f, -1f)
            }
        }
        return Bitmap.createBitmap(input, 0, 0, input.width, input.height, matrix, true)
    }
}