package bob.e2e.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import bob.e2e.model.Keypad
import bob.e2e.service.KeypadService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/keypad")
class KeypadController(private val keypadService: KeypadService) {

    @GetMapping("/generate")
    fun generateKeypad(): Keypad = keypadService.generateKeypad()

    @GetMapping("/validate/{id}")
    fun validateKeypad(@PathVariable id: String): Boolean {
        val keypad = keypadService.getKeypad(id)
        return keypad != null && keypad.expiration > System.currentTimeMillis()
    }
}