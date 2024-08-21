package kr.bob.e2ekeypad_BE.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.bob.e2ekeypad_BE.service.KeypadService
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller

@Tag(name = "Keypad API", description = "API for generating and validating keypads")
@Controller
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/keypad")
class KeypadController2(
    private val keypadService: KeypadService,
    private val restTemplate: RestTemplate,
) {

    @Operation(summary = "Validate user input")
    @PostMapping("/keyValidate")
    @ResponseBody
    fun validateKeypadInput(
        @RequestBody data: Map<String, Any>
    ): ResponseEntity<String> {
        val encUserInput = data["userInput"] as? String
            ?: return ResponseEntity("Invalid userInput", HttpStatus.BAD_REQUEST)
        val keypadId = data["keypad_id"] as? String
            ?: return ResponseEntity("Invalid keypad_id", HttpStatus.BAD_REQUEST)


        // Retrieve keyHashMap from Redis using keypadId
        val keyHashMap = keypadService.getKeyHashMapFromRedis(keypadId)
            ?: return ResponseEntity("Invalid keypad_id or no data found", HttpStatus.NOT_FOUND)

        // Prepare the data to be sent to the external server
        val requestBody = mapOf(
            "userInput" to encUserInput,
            "keyHashMap" to keyHashMap
        )
        println("Request Body: ${requestBody}")

        // Prepare headers if needed
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        // Send the request to the external server
        val requestEntity = HttpEntity(requestBody, headers)
        val responseEntity = restTemplate.exchange(
            "http://146.56.119.112:8081/auth",
            HttpMethod.POST,
            requestEntity,
            String::class.java
        )
        println("Response Body: ${responseEntity.body}")
        println("Response Status: ${responseEntity.statusCode}")

        // Return the response from the external server
        return ResponseEntity(responseEntity.body, responseEntity.statusCode)
    }
}
