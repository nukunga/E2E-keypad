package kr.bob.e2ekeypad_BE.util

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

object RSAUtil {

    fun loadPublicKey(keyPath: String): PublicKey {
        val keyBytes = this::class.java.getResourceAsStream(keyPath)?.readBytes()
            ?: throw IllegalArgumentException("Public key file not found: $keyPath")

        // PEM 파일에서 BEGIN과 END 라인을 제거합니다.
        val keyString = String(keyBytes).replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\n", "")
        val decodedKey = Base64.getDecoder().decode(keyString)

        val keySpec = X509EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    fun encrypt(publicKey: PublicKey, data: String): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.toByteArray()))
    }

}
