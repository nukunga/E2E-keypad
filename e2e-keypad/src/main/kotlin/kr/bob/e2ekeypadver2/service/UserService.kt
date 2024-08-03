package kr.bob.e2ekeypadver2.service

import kr.bob.e2ekeypadver2.model.User
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class UserService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun saveUser(user: User) {
        redisTemplate.opsForHash<String, String>().put("user:${user.id}", "password", user.password)
    }

    fun getUserPassword(id: String): String? {
        return redisTemplate.opsForHash<String, String>().get("user:$id", "password")
    }

    fun getAllUsers(): List<User> {
        val keys = redisTemplate.keys("user:*") ?: return emptyList()
        return keys.map { key ->
            val id = key.removePrefix("user:")
            val password = redisTemplate.opsForHash<String, String>().get(key, "password")
            User(id, password ?: "")
        }
    }
}
