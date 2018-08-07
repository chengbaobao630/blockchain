package cc.home.blockchain.util

import java.security.*
import java.util.*


/**
 * @author chengcheng
 */
class  StringUtils private constructor(){

    companion object {
        fun applySha256(input: String): String {
            try {
                val digest = MessageDigest.getInstance("SHA-256")
                //Applies sha256 to our input,
                val hash = digest.digest(input.toByteArray(charset("UTF-8")))
                val hexString = StringBuilder() // This will contain hash as hexidecimal
                for (i in hash.indices) {
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }
                return hexString.toString()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        //Applies ECDSA Signature and returns the result ( as bytes ).
        fun applyECDSASig(privateKey: PrivateKey, input: String): ByteArray {
            val dsa: Signature
            var output = ByteArray(0)
            try {
                dsa = Signature.getInstance("ECDSA", "BC")
                dsa.initSign(privateKey)
                val strByte = input.toByteArray()
                dsa.update(strByte)
                val realSig = dsa.sign()
                output = realSig
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            return output
        }

        //Verifies a String signature
        fun verifyECDSASig(publicKey: PublicKey, data: String, signature: ByteArray): Boolean {
            try {
                val ecdsaVerify = Signature.getInstance("ECDSA", "BC")
                ecdsaVerify.initVerify(publicKey)
                ecdsaVerify.update(data.toByteArray())
                return ecdsaVerify.verify(signature)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        fun getStringFromKey(key: Key): String {
            return Base64.getEncoder().encodeToString(key.getEncoded())
        }


    }

}