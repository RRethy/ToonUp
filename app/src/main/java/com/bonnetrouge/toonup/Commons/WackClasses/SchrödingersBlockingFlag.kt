package com.bonnetrouge.toonup.Commons.WackClasses

class SchrödingersBlockingFlag(initBlocking: Boolean) {

    var isBlocking: Boolean = initBlocking
        private set(value) {
            field = value
        }
    private var blockingCommutator: Int = if (initBlocking) 1 else 0

    fun reduceBlock() {
        if (blockingCommutator != 0) if (blockingCommutator-- == 1) isBlocking = false
    }

    fun addBlock() {
        blockingCommutator++
        isBlocking = true
    }
}