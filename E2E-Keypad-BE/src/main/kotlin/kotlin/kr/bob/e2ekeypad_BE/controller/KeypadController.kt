package kr.bob.e2ekeypad_BE.controller

import kr.bob.e2ekeypad_BE.service.KeypadService
import kr.bob.e2ekeypad_BE.util.Base64ImageEncoder
import kr.bob.e2ekeypad_BE.util.ImageCombiner
import kr.bob.e2ekeypad_BE.util.RSAUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.security.PublicKey

@Tag(name = "Keypad API", description = "API for generating and validating keypads")
@Controller
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/keypad")
class KeypadController(
    private val keypadService: KeypadService,
) {
    private val publicKey: PublicKey = RSAUtil.loadPublicKey("/key/public_key.pem")

    @Operation(summary = "Generate a new keypad")
    @GetMapping("/generate")
    @ResponseBody
    fun generateKeypad(): Map<String, Any> {
        val keypad = keypadService.generateKeypad()

        // 이미지 경로를 설정할 때 원래 키 값을 사용합니다.
        val images = keypad.keyMappings.map { (key, value) ->
            val imagePath = if (key == "blank1" || key == "blank2") {
                "/static/images/_blank.png"
            } else {
                "/static/images/_$key.png"
            }
            Base64ImageEncoder.encodeToBufferedImage(imagePath)
        }

        // 이미지를 결합합니다.
        val combinedImage = ImageCombiner.combineImages(images, 3, 4)

        // 결합된 이미지를 BASE64로 인코딩합니다.
        val combinedImageBase64 = ImageCombiner.bufferedImageToBase64(combinedImage)


        val response = mapOf(
                "keys" to keypad.keyMappings.values.toList(),
                "keypad_id" to keypad.id,
                "image" to "data:image/png;base64,$combinedImageBase64",
                "keyboardTimestamp" to System.currentTimeMillis()
        )

        return response
    }
}

