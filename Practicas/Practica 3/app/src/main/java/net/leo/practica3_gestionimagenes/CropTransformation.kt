package net.leo.practica3_gestionimagenes

import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation

class CropTransformation(
    private val startX: Int,
    private val startY: Int,
    private val width: Int,
    private val height: Int
) : Transformation {
    // Solo define cacheKey como propiedad, no como método
    override val cacheKey: String = "crop=$startX,$startY,$width,$height"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val w = minOf(input.width - startX, width)
        val h = minOf(input.height - startY, height)

        if (w <= 0 || h <= 0) return input

        return Bitmap.createBitmap(input, startX, startY, w, h)
    }
}