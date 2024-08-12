package kr.bob.e2ekeypad_BE.service

import kr.bob.e2ekeypad_BE.model.Keypad
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.*

@Service
class KeypadService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun generateKeypad(): Keypad {
        val keypad = Keypad()
        val keys = (0..9).map { it.toString() } + listOf("blank1", "blank2")
        val shuffledKeys = keys.shuffled().take(12)  // 3x4 grid

        val keyMappings = shuffledKeys.associate { key ->
            val hash = hashKey(key)
            hash to key
        }

        keypad.keyMappings = keyMappings
        redisTemplate.opsForHash<String, String>().putAll("keypad:${keypad.id}", keyMappings)

        return keypad
    }

    fun validateInput(userId: String, keypadId: Long, hashedInputs: List<String>): Boolean {
        val storedMappings = redisTemplate.opsForHash<String, String>().entries("keypad:$keypadId")
        val originalInputs = hashedInputs.map { hash ->
            storedMappings[hash]
        }.joinToString("")

        val storedPassword = redisTemplate.opsForHash<String, String>().get("user:$userId", "password")
        return storedPassword == originalInputs
    }

    private fun hashKey(key: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(key.toByteArray())
        return Base64.getEncoder().encodeToString(hash)
    }
}
