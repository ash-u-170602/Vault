package com.example.vault.aes_encryption

import com.example.vault.utils.Constants.SECRET_KEY
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {
    init {
        Security.addProvider(BouncyCastleProvider())
    }

    private val iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) // 16-byte IV

    fun encrypt(data: ByteArray): ByteArray {
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(iv))
        return cipher.doFinal(data)
    }

    fun decrypt(data: ByteArray): ByteArray {
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(iv))
        return cipher.doFinal(data)
    }
}
