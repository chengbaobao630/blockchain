package cc.home.blockchain.wallet

import cc.home.blockchain.core.Block
import cc.home.blockchain.util.StringUtils
import java.security.PublicKey
import java.security.PrivateKey



/**
 * @author chengcheng
 */
class Transaction(private val sender: PublicKey,
                  private val reciepient: PublicKey,
                  private val value: Float = 0f,
                  private val transactionInputs: List<TransactionInput>) {

    companion object {
        var sequence = 0
    }

    var transactionId: String = ""
    lateinit var signature: ByteArray
    lateinit var transactionOutputs: List<TransactionOutput>

    // This Calculates the transaction hash (which will be used as its Id)
    private fun calulateHash(): String {
        sequence++ //increase the sequence to avoid 2 identical transactions having the same hash
        return StringUtils.applySha256(
                "${StringUtils.getStringFromKey(sender)}${
                        StringUtils.getStringFromKey(reciepient)}" +
                        "$value" +
                        "$sequence")

    }

    //Signs all the data we dont wish to be tampered with.
    fun generateSignature(privateKey: PrivateKey) {
        val data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(reciepient) + value
        signature = StringUtils.applyECDSASig(privateKey, data)
    }

    //Verifies the data we signed hasnt been tampered with
    fun verifiySignature(): Boolean {
        val data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(reciepient) + value
        return StringUtils.verifyECDSASig(sender, data, signature)
    }
}