package cc.home.blockchain.core

import cc.home.blockchain.util.StringUtils


/**
 * @author chengcheng
 */
data class Block(val data:String,val previousHash:String,val timestamp: Long = System.currentTimeMillis()){


    internal var hash:String

    private var nonce:Int = 0

    init {
        this.hash=calculateHash()
    }

    fun calculateHash(): String {
        return StringUtils.applySha256("$previousHash$timestamp$nonce$data")
    }

    fun mineBlock(difficulty: Int) {
        val target = String(CharArray(difficulty)).replace('\u0000', '0') //Create a string with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++
            hash = calculateHash()
        }
        System.out.println("Block Mined!!! : $hash+${hash.length}")
    }
}

fun main(args: Array<String>) {

    val genesisBlock = Block("Hi im the first block", "0")
    System.out.println("Hash for block 1 : " + genesisBlock.hash)

    val secondBlock = Block("Yo im the second block", genesisBlock.hash)
    System.out.println("Hash for block 2 : " + secondBlock.hash)

    val thirdBlock = Block("Hey im the third block", secondBlock.hash)
    System.out.println("Hash for block 3 : " + thirdBlock.hash)

}