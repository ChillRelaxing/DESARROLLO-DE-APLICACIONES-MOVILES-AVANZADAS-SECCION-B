package net.leo.practica3_gestionimagenes

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import coil.size.Size
import coil.transform.Transformation

class GrayscaleTransformation : Transformation {
    // Solo utiliza la propiedad cacheKey, no el método
    override val cacheKey: String = "grayscale"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val output = Bitmap.createBitmap(
            input.width,
            input.height,
            input.config ?: Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
                setSaturation(0f)
            })
        }
        canvas.drawBitmap(input, 0f, 0f, paint)
        return output
    }
}