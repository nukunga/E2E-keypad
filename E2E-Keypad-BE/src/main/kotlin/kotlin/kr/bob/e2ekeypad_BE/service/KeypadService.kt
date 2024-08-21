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
        val keys = (0..9).map { it.toString() }  // Only numeric keys
        val shuffledKeys = keys.shuffled().take(10)  // Shuffling the numeric keys for randomness

        // Create a map where the numeric key is the key, and the hash of the key is the value
        val keyMappings = shuffledKeys.associate { key ->
            key to hashKey(key)
        }

        // Store the map in Redis
        redisTemplate.opsForHash<String, String>().putAll("keypad:${keypad.id}", keyMappings)

        // Set the keyMappings in the keypad object
        keypad.keyMappings = keyMappings

        return keypad
    }

    fun hashKey(key: String): String {
        // Implement your hashing logic here, for example using SHA-256
        val md = java.security.MessageDigest.getInstance("SHA-256")
        return md.digest(key.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }.substring(0, 40)   // Convert to hex string
    }




    fun getKeyHashMapFromRedis(keypadId: String): Map<String, String>? {
        val redisKey = "keypad:$keypadId"

        // Retrieve all the hash entries for the given keypad ID
        val keyMappings: MutableMap<Any, Any> = redisTemplate.opsForHash<Any, Any>().entries(redisKey)

        // Convert the Map<Object, Object> to Map<String, String> for easier usage
        return keyMappings?.map { (key, value) -> key.toString() to value.toString() }?.toMap()
    }
}
