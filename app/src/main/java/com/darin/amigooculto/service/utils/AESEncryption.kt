package com.darin.amigooculto.service.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class AESEncryption {

    companion object {
        private const val SECRET = "Yq3t6w9y\$B&E)H@McQfTjWnZr4u7x!A%"
        private const val SALT = "QWlGNHNhMTJTQWZ2bGhpV3U="
        private const val IV = "bVQzNFNhRkQ1Njc4UUFaWA=="

        fun encrypt(strToEncrypt: String) :  String?
        {
            try
            {
                val ivParameterSpec = IvParameterSpec(Base64.decode(IV, Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec =  PBEKeySpec(SECRET.toCharArray(), Base64.decode(SALT, Base64.DEFAULT), 10000, 256)
                val tmp = factory.generateSecret(spec)
                val secretKey =  SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
                return Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
            }
            catch (e: Exception)
            {
                println("Erro ao criptografar: $e")
            }
            return null
        }

        fun decrypt(strToDecrypt : String) : String? {
            try
            {

                val ivParameterSpec =  IvParameterSpec(Base64.decode(IV, Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec =  PBEKeySpec(SECRET.toCharArray(), Base64.decode(SALT, Base64.DEFAULT), 10000, 256)
                val tmp = factory.generateSecret(spec);
                val secretKey =  SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
                return  String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
            }
            catch (e : Exception) {
                println("Erro ao descriptografar: $e");
            }
            return null
        }

    }

}