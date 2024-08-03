package kr.bob.e2ekeypadver2.util

import java.util.*
import java.io.File
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

object Base64ImageEncoder {

    fun encodeToBase64(image: BufferedImage): String {
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "png", outputStream)
        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    fun encodeImageToBase64(imagePath: String): String {
        val file = File(imagePath)
        val bytes = file.readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }

    fun generateImageForNumber(number: String): BufferedImage {
        val image = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()
        graphics.drawString(number, 50, 50)
        graphics.dispose()
        return image
    }

    fun generateBlankImage(): BufferedImage {
        val image = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()
        graphics.drawRect(0, 0, 99, 99)
        graphics.dispose()
        return image
    }
}
