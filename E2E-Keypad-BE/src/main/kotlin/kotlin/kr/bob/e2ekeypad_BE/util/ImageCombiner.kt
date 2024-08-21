package kr.bob.e2ekeypad_BE.util

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import java.util.Base64

object ImageCombiner {

    fun combineImages(images: List<BufferedImage>, rows: Int, cols: Int): BufferedImage {
        val width = images[0].width * cols
        val height = images[0].height * rows
        val combinedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        val g: Graphics2D = combinedImage.createGraphics()

        for (i in images.indices) {
            val x = (i % cols) * images[i].width
            val y = (i / cols) * images[i].height
            g.drawImage(images[i], x, y, null)
        }

        g.dispose()
        return combinedImage
    }

    fun bufferedImageToBase64(image: BufferedImage): String {
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "png", outputStream)
        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }
}
