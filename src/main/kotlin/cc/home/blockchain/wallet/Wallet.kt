package cc.home.blockchain.wallet

import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec


/**
 * @author chengcheng
 */
class Wallet{

    lateinit var  privateKey:PrivateKey
    lateinit var  publicKey: PublicKey

    init {
        generateKeyPair()
    }

    private fun generateKeyPair() {
        try {
            val keyGen = KeyPairGenerator.getInstance("ECDSA", "BC")
            val random = SecureRandom.getInstance("SHA1PRNG")
            val ecSpec = ECGenParameterSpec("prime192v1")
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random)   //256 bytes provides an acceptable security level
            val keyPair = keyGen.generateKeyPair()
            // Set the public and private keys from the keyPair
            privateKey = keyPair.private
            publicKey = keyPair.public
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}