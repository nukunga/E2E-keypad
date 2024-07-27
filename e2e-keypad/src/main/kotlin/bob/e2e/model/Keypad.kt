package bob.e2e.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("Keypad")
data class Keypad(
    @Id val id: String,
    val keys: List<String>,
    val expiration: Long
) : Serializable