package bob.e2e.service

import bob.e2e.model.Keypad
import bob.e2e.repository.KeypadRepository
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.*

@Service
class KeypadService(private val keypadRepository: KeypadRepository) {

    fun generateKeypad(): Keypad {
        val keys = (0..9).shuffled().map { hash(it.toString()) }
        val id = UUID.randomUUID().toString()
        val expiration = System.currentTimeMillis() + 60000 // 1분 유효 기간

        val keypad = Keypad(id, keys, expiration)
        keypadRepository.save(keypad)
        return keypad
    }

    private fun hash(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun getKeypad(id: String): Keypad? {
        return keypadRepository.findById(id).orElse(null)
    }
}