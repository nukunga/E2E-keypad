package kr.bob.e2ekeypad.controller

import kr.bob.e2ekeypadver2.model.User
import kr.bob.e2ekeypadver2.service.KeypadService
import kr.bob.e2ekeypadver2.service.UserService
import kr.bob.e2ekeypadver2.util.Base64ImageEncoder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Tag(name = "Keypad API", description = "API for generating and validating keypads")
@Controller
@RequestMapping("/api/keypad")
class KeypadController(
    private val keypadService: KeypadService,
    private val userService: UserService
) {

    @Operation(summary = "Generate a new keypad")
    @GetMapping("/generate")
    @ResponseBody
    fun generateKeypad(): Map<String, Any> {
        val keypad = keypadService.generateKeypad()
        val keyMappingsWithImages = keypad.keyMappings.map { (hash, key) ->
            val imagePath = if (key == "blank1" || key == "blank2") {
                "src/main/resources/static/images/_blank.png"
            } else {
                "src/main/resources/static/images/_$key.png"
            }
            val image = Base64ImageEncoder.encodeImageToBase64(imagePath)
            mapOf("hash" to hash, "image" to image)
        }

        val response = mapOf(
            "keypadId" to keypad.id,
            "keyMappings" to keyMappingsWithImages.chunked(4)  // 3x4 grid
        )
        return response
    }

    @Operation(summary = "Validate keypad input")
    @GetMapping("/validate")
    fun validateInput(
        @RequestParam userId: String,
        @RequestParam keypadId: Long,
        @RequestParam hashedInputs: List<String>,
        model: Model
    ): String {
        val isValid = keypadService.validateInput(userId, keypadId, hashedInputs)
        model.addAttribute("isValid", isValid)
        return "result"
    }

    @GetMapping("/user/add")
    fun showAddUserForm(): String {
        return "addUser"
    }

    @PostMapping("/user/add")
    fun addUser(@RequestParam id: String, @RequestParam password: String): String {
        userService.saveUser(User(id, password))
        return "redirect:/api/keypad/user/add?success"
    }
}
