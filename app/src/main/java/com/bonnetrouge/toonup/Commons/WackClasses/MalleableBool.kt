package com.bonnetrouge.toonup.Commons.WackClasses

class MalleableBool(boolean: Boolean) {
    var bool = boolean
        private set
    var isHardValue = false

    fun softBool(boolean: Boolean) {
        if (!isHardValue) bool = boolean
    }

    fun hardBool(boolean: Boolean) {
        bool = boolean
    }

    fun makeHard() {
        isHardValue = true
    }

    fun makeSoft() {
        isHardValue = false
    }
}
