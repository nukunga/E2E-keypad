package kr.bob.e2ekeypad_BE.util

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object Base64ImageEncoder {

    fun encodeToBufferedImage(imagePath: String): BufferedImage {
        val inputStream = this::class.java.getResourceAsStream(imagePath)
        if (inputStream == null) {
            throw IllegalArgumentException("File not found: $imagePath")
        }
        return ImageIO.read(inputStream)
    }


}
