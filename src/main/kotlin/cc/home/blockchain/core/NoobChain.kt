package cc.home.blockchain.core

import com.google.gson.GsonBuilder


/**
 * @author chengcheng
 */
class NoobChain{

    internal val blockchain = ArrayList<Block>()

    fun isChainValid(): Boolean {
        var currentBlock: Block
        var previousBlock: Block

        //loop through blockchain to check hashes:
        for (i in 1 until blockchain.size) {
            currentBlock = blockchain[i]
            previousBlock = blockchain[i - 1]
            //compare registered hash and calculated hash:
            if (currentBlock.hash != currentBlock.calculateHash()) {
                println("Current Hashes not equal")
                return false
            }
            //compare previous hash and registered previous hash
            if (previousBlock.hash != currentBlock.previousHash) {
                println("Previous Hashes not equal")
                return false
            }
        }
        return true
    }
}

fun main(args: Array<String>) {
    //add our blocks to the blockchain ArrayList:
    val noobChain=NoobChain()
    val blockchain = noobChain.blockchain
    val isChainValid = {noobChain.isChainValid()}

    val difficulty = 1

    blockchain.add(Block("Hi im the first block", "0"))
    println("Trying to Mine block 1... ")
    blockchain.get(0).mineBlock(difficulty)

    blockchain.add(Block("Yo im the second block", blockchain.get(blockchain.size - 1).hash))
    println("Trying to Mine block 2... ")
    blockchain.get(1).mineBlock(difficulty)

    blockchain.add(Block("Hey im the third block", blockchain.get(blockchain.size - 1).hash))
    println("Trying to Mine block 3... ")
    blockchain.get(2).mineBlock(difficulty)

    System.out.println("\nBlockchain is Valid: " + isChainValid())

    val blockchainJson = GsonBuilder().setPrettyPrinting().create().toJson(blockchain)
    println("\nThe block chain: ")
    println(blockchainJson)
}